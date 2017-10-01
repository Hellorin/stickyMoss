package com.hellorin.stickyMoss.documents.services;

import com.hellorin.stickyMoss.documents.DocumentsFactory;
import com.hellorin.stickyMoss.documents.domain.CV;
import com.hellorin.stickyMoss.documents.domain.Document;
import com.hellorin.stickyMoss.documents.domain.DocumentFileFormat;
import com.hellorin.stickyMoss.documents.exceptions.DocumentNotFoundException;
import com.hellorin.stickyMoss.documents.factories.DocumentServicesFactory;
import com.hellorin.stickyMoss.documents.repositories.CVRepository;
import com.hellorin.stickyMoss.documents.repositories.DocumentRepository;
import lombok.NonNull;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by hellorin on 28.09.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class GenericDocumentServiceTest {

    @Configuration
    static class CVDocumentServiceTestContextConfiguration {
        @Bean
        public IGenericDocumentService documentService() {
            return new GenericDocumentService();
        }

        @Bean
        public DocumentServicesFactory documentServicesFactory() { return new DocumentServicesFactory(); }

        @Bean
        public DocumentsFactory documentsFactory() { return spy(new DocumentsFactory()); }

        @Bean
        public CVDocumentService service() { return spy(new CVDocumentService()); }

        @Bean
        public AbstractDocumentService[] abstractDocumentServices() {
            return new AbstractDocumentService[]{ service() };
        }

    }

    @Autowired
    private IGenericDocumentService documentService;

    @Autowired
    private DocumentServicesFactory documentServicesFactory;

    @Autowired
    private DocumentsFactory documentsFactory;

    @Autowired
    private CVDocumentService cvService;

    @MockBean
    private DocumentRepository documentRepository;

    @MockBean
    private CVRepository cvRepository;

    @Test
    public void createDocumentCV() throws Exception {
        // Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file.pdf", new byte[]{0x25, 0x50, 0x44, 0x46, 0x2D, 0x1,0x1,0x1,0x1,0x1,0x1,0x1,});

        // When
        documentService.createDocument(mockMultipartFile, "CV");

        // Then
        verify(documentsFactory, times(1)).buildDocumentFromMultipartFile(mockMultipartFile, "CV");
        verify(cvService, times(1)).create(any(CV.class));
    }

    @Test
    public void getExistingDocument() {
        // Given
        when(documentRepository.exists(1L)).thenReturn(true);
        when(cvRepository.exists(1L)).thenReturn(true);
        CV cv = mock(CV.class);
        when(cvRepository.getOne(1L)).thenReturn(cv);

        // When
        Document document = documentService.getDocument(1L);

        // Then
        verify(documentRepository, times(1)).exists(1L);
        verify(cvRepository, times(1)).exists(1L);
        verify(cvRepository, times(1)).getOne(1L);

        assertEquals(document, cv);
    }

    @Test(expected = DocumentNotFoundException.class)
    public void getInexistingDocument() {
        // Given
        when(documentRepository.exists(1L)).thenReturn(false);

        // When
        documentService.getDocument(1L);
    }

    @Test
    public void deleteExistingDocument() {
        // Given
        when(documentRepository.exists(1L)).thenReturn(true);

        // When
        documentService.delete(1L);

        // Then
        verify(documentRepository, timeout(1)).exists(1L);
        verify(documentRepository, timeout(1)).delete(1L);
    }

    @Test(expected = DocumentNotFoundException.class)
    public void deleteInexistingDocument() {
        // Given
        when(documentRepository.exists(1L)).thenReturn(false);

        // When
        documentService.delete(1L);
    }

    @Test
    public void modifyExistingDocument() {
        // Given
        when(documentRepository.exists(1L)).thenReturn(true);
        when(cvRepository.exists(1L)).thenReturn(true);

        CV cvBefore = new CV("name", DocumentFileFormat.PDF, new byte[]{0x1});
        CV cvModify = new CV("newName", DocumentFileFormat.PDF, new byte[]{0x2});

        Whitebox.setInternalState(cvModify, "id", 1L);
        Whitebox.setInternalState(cvBefore, "id", 1L);

        when(cvRepository.getOne(1L)).thenReturn(cvBefore);

        // When
        Document docAfter = documentService.modifyDocument(cvModify);

        // Then
        assertNotNull(docAfter);
        assertEquals("newName", docAfter.getName());
        assertEquals(1, docAfter.getContent().length);
        assertEquals(0x2, docAfter.getContent()[0]);


    }

    @Test(expected =  DocumentNotFoundException.class)
    public void modifyInexistingDocument() {
        // Given
        when(documentRepository.exists(1L)).thenReturn(false);

        Document docBefore = mock(CV.class);
        when(docBefore.getId()).thenReturn(1L);

        // When
        documentService.modifyDocument(docBefore);
    }
}
