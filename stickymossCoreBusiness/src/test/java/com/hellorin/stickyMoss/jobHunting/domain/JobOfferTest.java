package com.hellorin.stickyMoss.jobHunting.domain;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by hellorin on 16.07.17.
 */
@RunWith(JUnit4.class)
public class JobOfferTest {

    @Test
    public void testConstructor1() {
        LocalDate now = LocalDate.now();

        JobOffer offer1 = new JobOffer(now, JobOfferStatus.OPEN);

        assertNotNull(offer1);
        assertNull(offer1.getId());
        assertEquals(JobOfferStatus.OPEN, offer1.getStatus());
        assertEquals(now, offer1.getDateDiscovered());

        JobOffer offer2 = new JobOffer(now, JobOfferStatus.CLOSE);

        assertNotNull(offer2);
        assertNull(offer2.getId());
        assertEquals(JobOfferStatus.CLOSE, offer2.getStatus());
        assertEquals(now, offer1.getDateDiscovered());

        JobOffer offer3 = new JobOffer(now);

        assertNotNull(offer3);
        assertNull(offer3.getId());
        assertEquals(JobOfferStatus.OPEN, offer3.getStatus());
        assertEquals(now, offer1.getDateDiscovered());
    }

    @Test
    public void testEquals() {
        LocalDate date = LocalDate.now();

        JobOffer offer0 = new JobOffer();
        JobOffer offer1 = new JobOffer(date);
        JobOffer offer2 = new JobOffer(date);
        JobOffer offer3 = new JobOffer(date);
        JobOffer offer4 = new JobOffer(date.plusDays(1));

        assertFalse(offer1.equals(null));
        assertFalse(offer1.equals(true));

        assertTrue(offer1.equals(offer1));

        assertTrue(offer1.equals(offer2));
        assertTrue(offer2.equals(offer1));

        assertTrue(offer1.equals(offer2));
        assertTrue(offer2.equals(offer3));
        assertTrue(offer1.equals(offer3));

        assertFalse(offer1.equals(offer4));

        assertEquals(offer1.hashCode(), offer2.hashCode());
        assertNotEquals(offer1.hashCode(), offer4.hashCode());
    }

    @Test
    public void testCloseJobOffer() {
        LocalDate date = LocalDate.now();
        JobOffer jobOffer = new JobOffer(date, JobOfferStatus.OPEN);

        jobOffer.close();

        assertEquals(date, jobOffer.getDateDiscovered());
        assertEquals(JobOfferStatus.CLOSE, jobOffer.getStatus());
    }
}
