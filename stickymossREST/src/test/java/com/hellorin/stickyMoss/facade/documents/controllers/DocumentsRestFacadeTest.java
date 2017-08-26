package com.hellorin.stickyMoss.facade.documents.controllers;

import com.hellorin.stickyMoss.StickyMossApplication;
import com.hellorin.stickyMoss.documents.dtos.CVDTO;
import com.hellorin.stickyMoss.documents.dtos.DocumentFileFormatDTO;
import com.hellorin.stickyMoss.documents.repositories.CVRepository;
import com.hellorin.stickyMoss.facade.AbstractRestControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.hamcrest.CoreMatchers.anything;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hellorin on 04.08.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {StickyMossApplication.class})
@AutoConfigureMockMvc
public class DocumentsRestFacadeTest extends AbstractRestControllerTest {

    @Autowired
    private CVRepository cvRepository;

    private String baseUrl;

    @Before
    public void setup() throws Exception {
        cvRepository.deleteAllInBatch();

        baseUrl = "/" + AbstractRestControllerTest.getRestControllerBaseURL(DocumentsRestFacadeController.class);
    }

    @Test
    public void testCreateNewCV() throws Exception {
        CVDTO cvdto = new CVDTO();
        cvdto.setName("file1");
        cvdto.setContent(new byte[]{0});
        cvdto.setFormat(DocumentFileFormatDTO.PDF);
        cvdto.setDateUploaded(new Date());

        mvc.perform(
                MockMvcRequestBuilders.put(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(cvdto)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.id", anything()));

    }

}
