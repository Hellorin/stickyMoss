package com.hellorin.stickyMoss.documents.domain;

import com.hellorin.stickyMoss.classification.domain.Tag;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by hellorin on 25.06.17.
 */
@RunWith(JUnit4.class)
public class CVTest {

    @Test(expected = NullPointerException.class)
    public void testConstructorNullName() {
        new CV(null, DocumentFileFormat.PDF, new byte[5], Sets.newHashSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEmptyName() {
        new CV("", DocumentFileFormat.PDF, new byte[5], Sets.newHashSet());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullFileFormat() {
        new CV("file1", null, new byte[5], Sets.newHashSet());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullContent() {
        new CV("file1", DocumentFileFormat.PDF, null, Sets.newHashSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEmptyContent() {
        new CV("file1", DocumentFileFormat.PDF, new byte[0], Sets.newHashSet());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullTags() {
        new CV("file1", DocumentFileFormat.PDF, new byte[1], null);
    }

    @Test
    public void testConstructor() {
        byte[] content = new byte[]{1};

        final String filename = "file1";
        CV cv = new CV(filename, DocumentFileFormat.PDF, content, Sets.newHashSet());

        assertNotNull(cv);
        assertNull(cv.getId());
        assertEquals(filename, cv.getName());
        assertEquals(DocumentFileFormat.PDF, cv.getFormat());
        assertEquals(content, cv.getContent());
        assertEquals(Sets.newHashSet(), cv.getTags());

        cv.setName("file2");
        assertEquals("file2", cv.getName());

        cv.setFormat(DocumentFileFormat.WORD);
        assertEquals(DocumentFileFormat.WORD, cv.getFormat());

        cv.setContent(new byte[]{5});
        assertEquals(5, cv.getContent() [0]);

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("tag"));

        cv.setTags(tags);
        assertEquals(tags, cv.getTags());
    }

    @Test
    public void testEquals() {
        byte[] content = new byte[]{1};
        byte[] content2 = new byte[]{2};

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("tag"));

        CV cv1 = new CV("file1", DocumentFileFormat.PDF, content, Sets.newHashSet());
        CV cv2 = new CV("file1", DocumentFileFormat.PDF, content, Sets.newHashSet());
        CV cv3 = new CV("file1", DocumentFileFormat.PDF, content, Sets.newHashSet());
        CV cv4 = new CV("file2", DocumentFileFormat.PDF, content, Sets.newHashSet());
        CV cv5 = new CV("file1", DocumentFileFormat.WORD, content, Sets.newHashSet());
        CV cv6 = new CV("file1", DocumentFileFormat.WORD, content2, Sets.newHashSet());
        CV cv7 = new CV("file1", DocumentFileFormat.WORD, content2, tags);

        assertFalse(cv1.equals(null));
        assertFalse(cv1.equals(false));

        assertTrue(cv1.equals(cv1));
        assertEquals(cv1.hashCode(), cv1.hashCode());

        assertTrue(cv1.equals(cv2));
        assertTrue(cv2.equals(cv1));
        assertEquals(cv1.hashCode(), cv2.hashCode());

        assertTrue(cv1.equals(cv2));
        assertTrue(cv2.equals(cv3));
        assertTrue(cv1.equals(cv3));
        assertEquals(cv1.hashCode(), cv3.hashCode());

        assertFalse(cv1.equals(cv4));
        assertFalse(cv1.equals(cv5));
        assertFalse(cv1.equals(cv6));
        assertFalse(cv1.equals(cv7));

    }

}
