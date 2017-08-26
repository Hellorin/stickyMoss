package com.hellorin.stickyMoss.jobHunting.domain;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by hellorin on 16.07.17.
 */
@RunWith(JUnit4.class)
public class JobOfferTest {

    @Test
    public void testConstructor() {
        JobOffer offer = new JobOffer();

        assertNotNull(offer);
        assertNull(offer.getId());
    }

    @Test
    @Ignore
    public void testEquals() {
        JobOffer offer1 = new JobOffer();
        JobOffer offer2 = new JobOffer();
        JobOffer offer3 = new JobOffer();

        assertFalse(offer1.equals(null));
        assertFalse(offer1.equals(true));

        assertFalse(offer1.equals(offer1));

        assertFalse(offer1.equals(offer2));
        assertFalse(offer2.equals(offer3));
        assertFalse(offer1.equals(offer3));

    }
}
