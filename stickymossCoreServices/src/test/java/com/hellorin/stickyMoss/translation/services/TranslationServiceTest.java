package com.hellorin.stickyMoss.translation.services;

import com.hellorin.stickyMoss.translation.domain.Translation;
import com.hellorin.stickyMoss.translation.repositories.TranslationRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by hellorin on 09.07.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TranslationServiceTest {
    @Configuration
    static class TranslationServiceTestContextConfiguration {
        @Bean
        public TranslationRepository translationRepository() {
            return mock(TranslationRepository.class);
        }

        @Bean
        public TranslationService translationService() {
            return new TranslationService();
        }
    }

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private TranslationService translationService;

    private File folder = new File(Paths.get(".","translations").toString());
    private File file = new File(Paths.get(".","translations", "translations1.csv").toString());
    private File file2 = new File(Paths.get(".","translations", "translations2.csv").toString());

    @Before
    public void setup() {
        Mockito.reset(translationRepository);
    }

    @After
    public void teardown() {
        file.delete();
        file2.delete();
        folder.delete();
    }

    @Test
    public void testRefreshNoFolder() throws Exception {
        // Given

        // When
        translationService.updateTranslations();

        // Then
        verify(translationRepository, times(0)).save(any(Translation.class));
    }

    @Test
    public void testRefreshEmptyFolder() throws Exception {
        // Given
        folder.mkdirs();

        // When
        translationService.updateTranslations();

        // Then
        verify(translationRepository, times(0)).save(any(Translation.class));
    }

    @Test
    public void testRefreshNormal() throws Exception {
        // Given
        when(translationRepository.findByKey("key1")).thenReturn(Optional.of(mock(Translation.class)));
        when(translationRepository.findByKey("key2")).thenReturn(Optional.empty());
        when(translationRepository.findByKey("key3")).thenReturn(Optional.empty());
        when(translationRepository.findByKey("key4")).thenReturn(Optional.empty());

        folder.mkdirs();
        file.createNewFile();

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("aaaa\n");
        bw.write("key1,translationFr,translationEn\n");
        bw.write("key2,translationFr,translationEn\n");
        bw.write("key3,translationFr\n");
        bw.close();
        fw.close();

        file2.createNewFile();
        fw = new FileWriter(file2);
        bw = new BufferedWriter(fw);
        bw.write("key4,translationFr,translationEn\n");
        bw.close();
        fw.close();

        // When
        translationService.updateTranslations();

        // Then
        verify(translationRepository, times(3)).findByKey(any(String.class));
        verify(translationRepository, times(2)).save(any(Translation.class));
    }

    @Test
    public void testGetTranslations() {
        // Given
        when(translationRepository.findByKey("key1")).thenReturn(Optional.of(mock(Translation.class)));
        when(translationRepository.findByKey("key2")).thenReturn(Optional.empty());

        // When
        Optional<Translation> trans1 = translationService.getTranslations("key1");
        Optional<Translation> trans2 = translationService.getTranslations("Key1");
        Optional<Translation> trans3 = translationService.getTranslations("key2");

        assertNotNull(trans1);
        assertNotNull(trans2);
        assertNotNull(trans3);
        assertTrue(trans1.isPresent());
        assertTrue(trans2.isPresent());
        assertFalse(trans3.isPresent());
    }
}
