package com.hellorin.stickyMoss.facade.documents.controllers;

import com.hellorin.stickyMoss.TestStickyMossConfiguration;
import com.hellorin.stickyMoss.documents.domain.CV;
import com.hellorin.stickyMoss.documents.domain.Document;
import com.hellorin.stickyMoss.documents.domain.DocumentFileFormat;
import com.hellorin.stickyMoss.documents.repositories.CVRepository;
import com.hellorin.stickyMoss.facade.AbstractRestControllerTest;
import org.apache.tika.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hellorin on 04.08.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestStickyMossConfiguration.class})
@AutoConfigureMockMvc
public class DocumentsRestFacadeTest extends AbstractRestControllerTest {

    @Autowired
    private CVRepository cvRepository;

    private String baseUrl;

    private Document document;

    @Before
    public void setup() throws Exception {
        cvRepository.deleteAllInBatch();

        byte[] data = "Hello world".getBytes();

        document = cvRepository.save(new CV("file.pdf",
            DocumentFileFormat.PDF,
                data));

        baseUrl = "/" + AbstractRestControllerTest.getRestControllerBaseURL(DocumentsRestFacadeController.class);
    }

    @Test
    public void testCreateCVinPDF() throws Exception {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("cv.pdf");

        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.pdf",
                MediaType.APPLICATION_PDF_VALUE, IOUtils.toByteArray(is));
        mvc.perform(fileUpload(baseUrl + "?type=CV").file(multipartFile))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCVinPDF() throws Exception {
        mvc.perform(get(baseUrl + "/" + document.getId() + "?type=CV"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(document.getContent()));
    }
}
