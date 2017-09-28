package com.hellorin.stickyMoss.documents.factories;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.hellorin.stickyMoss.documents.DocumentsFactory;
import com.hellorin.stickyMoss.documents.exceptions.UnsupportedFileFormatException;
import com.hellorin.stickyMoss.documents.repositories.DocumentRepository;
import com.hellorin.stickyMoss.documents.services.AbstractDocumentService;
import com.hellorin.stickyMoss.documents.services.CVDocumentService;
import com.hellorin.stickyMoss.documents.services.GenericDocumentService;
import com.hellorin.stickyMoss.documents.services.IGenericDocumentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by hellorin on 05.08.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DocumentServicesFactoryTest {

    @Configuration
    static class DocumentServicesFactoryTestContextConfiguration {
        @Bean
        public DocumentServicesFactory documentServicesFactory() {
            return new DocumentServicesFactory();
        }

        @Bean
        public DocumentsFactory documentsFactory() {
            return new DocumentsFactory();
        }

        @Bean
        public AbstractDocumentService[] abstractDocumentServices() {
            return new AbstractDocumentService[]{new CVDocumentService()};
        }

        @Bean
        public IGenericDocumentService genericDocumentService() {
            return new GenericDocumentService();
        }
    }

    @Autowired
    private DocumentServicesFactory documentServicesFactory;

    @Autowired
    private IGenericDocumentService genericDocumentService;

    @Autowired
    private DocumentsFactory documentsFactory;

    @MockBean
    private DocumentRepository documentRepository;

    @Test
    public void testGetCVservice() {
        AbstractDocumentService service = documentServicesFactory.getServiceByDocumentType("CV");

        assertNotNull(service);
        assertTrue(service instanceof CVDocumentService);

        AbstractDocumentService service2 = documentServicesFactory.getServiceByDocumentType("CV");

        assertNotNull(service2);
        assertTrue(service2 instanceof CVDocumentService);
    }

    @Test(expected = UnsupportedFileFormatException.class)
    public void testGetUnknownService() {
        documentServicesFactory.getServiceByDocumentType("MotivationLetter");
    }
}
