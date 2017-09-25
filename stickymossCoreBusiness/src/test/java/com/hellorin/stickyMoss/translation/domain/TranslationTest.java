package com.hellorin.stickyMoss.translation.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by hellorin on 16.07.17.
 */
@RunWith(JUnit4.class)
public class TranslationTest {
    @Test
    public void testConstructor() {
        Map<String, String> translations = new HashMap<>();
        translations.put("fr", "clé1");
        translations.put("en", "key1");

        Map<String, String> translations2 = new HashMap<>();
        translations.put("fr", "clés1");
        translations.put("en", "keys1");

        Translation translation = new Translation("key1", translations);

        assertNotNull(translation);
        assertNull(translation.getId());
        assertEquals("key1", translation.getKey());
        assertEquals(translations, translation.getTranslationsByLanguages());

        translation.setTranslationsByLanguages(translations2);
        assertEquals(translations2, translation.getTranslationsByLanguages());
    }
}
