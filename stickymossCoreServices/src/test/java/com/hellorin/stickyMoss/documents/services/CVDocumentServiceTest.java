package com.hellorin.stickyMoss.documents.services;

import com.hellorin.stickyMoss.documents.domain.CV;
import com.hellorin.stickyMoss.documents.repositories.CVRepository;
import com.hellorin.stickyMoss.documents.repositories.DocumentRepository;
import com.hellorin.stickyMoss.jobHunting.repositories.ApplicantRepository;
import com.hellorin.stickyMoss.jobHunting.services.ApplicantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yaml.snakeyaml.events.Event;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by hellorin on 05.08.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CVDocumentServiceTest {
    @Configuration
    static class CVDocumentServiceTestContextConfiguration {
        @Bean
        public CVDocumentService CVDocumentService() {
            return new CVDocumentService();
        }

        @Bean
        public CVRepository cvRepositoryRepository() {
            return mock(CVRepository.class);
        }
    }


    @Autowired
    private CVDocumentService cvDocumentService;

    @Test
    public void testGetType() {
        assertEquals(CV.class, cvDocumentService.getType());
    }
}
