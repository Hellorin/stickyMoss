package com.hellorin.stickyMoss.classification.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Created by hellorin on 25.06.17.
 */
@RunWith(JUnit4.class)
public class TagTest {

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNull() {
        new Tag(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEmpty() {
        new Tag("");
    }

    @Test
    public void testConstructorWorking() {
        final String tagName = "this is a tag";
        Tag tag = new Tag(tagName);

        assertNotNull(tag);
        assertNull(tag.getId());
        assertEquals(tagName, tag.getName());
    }

    @Test
    public void testEquals() {
        final Tag tag1 = new Tag("tag1");
        final Tag tag2 = new Tag("tag1");
        final Tag tag3 = new Tag("tag1");
        final Tag tag4 = new Tag("tag2");

        assertFalse(tag1.equals(null));

        assertTrue(tag1.equals(tag1));
        assertEquals(tag1.hashCode(), tag1.hashCode());

        assertTrue(tag1.equals(tag2));
        assertTrue(tag2.equals(tag1));
        assertEquals(tag1.hashCode(), tag2.hashCode());

        assertTrue(tag1.equals(tag2));
        assertTrue(tag2.equals(tag3));
        assertTrue(tag1.equals(tag3));
        assertEquals(tag1.hashCode(), tag3.hashCode());

        assertFalse(tag1.equals(tag4));
        assertFalse(tag4.equals(tag1));
        assertNotEquals(tag1.hashCode(), tag4.hashCode());

        assertFalse(tag1.equals("random"));
    }

}
