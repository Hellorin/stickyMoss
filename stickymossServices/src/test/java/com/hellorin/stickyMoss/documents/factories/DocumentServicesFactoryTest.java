package com.hellorin.stickyMoss.documents.factories;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.hellorin.stickyMoss.documents.exceptions.UnsupportedFileFormatException;
import com.hellorin.stickyMoss.documents.services.AbstractDocumentService;
import com.hellorin.stickyMoss.documents.services.CVDocumentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
        public AbstractDocumentService[] abstractDocumentServices() {
            return new AbstractDocumentService[]{new CVDocumentService()};
        }
    }

    @Autowired
    private DocumentServicesFactory documentServicesFactory;

    @Test
    public void testGetCVservice() {
        AbstractDocumentService service = documentServicesFactory.getServiceByDocumentType("CV", false);

        assertNotNull(service);
        assertTrue(service instanceof CVDocumentService);

        AbstractDocumentService service2 = documentServicesFactory.getServiceByDocumentType("CV");

        assertNotNull(service2);
        assertTrue(service2 instanceof CVDocumentService);

        AbstractDocumentService service3 = documentServicesFactory.getServiceByDocumentType("CVDTO", true);

        assertNotNull(service3);
        assertTrue(service3 instanceof CVDocumentService);
    }

    @Test(expected = UnsupportedFileFormatException.class)
    public void testGetUnknownService() {
        documentServicesFactory.getServiceByDocumentType("MotivationLetter", false);
    }
}
