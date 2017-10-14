package com.hellorin.stickyMoss.jobHunting.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by hellorin on 16.07.17.
 */
@RunWith(JUnit4.class)
public class JobApplicationTest {

    @Test
    public void testConstructorWithStatus() {
        Applicant applicant = mock(Applicant.class);
        LocalDate dateSubmitted = LocalDate.now();
        JobApplication jobApplication = new JobApplication(dateSubmitted, applicant, JobApplicationStatus.SUBMITTED);

        assertNotNull(jobApplication);
        assertNull(jobApplication.getId());
        assertEquals(dateSubmitted, jobApplication.getDateSubmitted());
        assertEquals(JobApplicationStatus.SUBMITTED, jobApplication.getStatus());

        LocalDate newDate = LocalDate.now();
        jobApplication.setDateSubmitted(newDate);
        jobApplication.setStatus(JobApplicationStatus.CANCELED);
        assertEquals(newDate, jobApplication.getDateSubmitted());
        assertEquals(JobApplicationStatus.CANCELED, jobApplication.getStatus());
    }

    @Test
    public void testConstructorWithoutStatus() {
        Applicant applicant = mock(Applicant.class);
        LocalDate dateSubmitted = LocalDate.now();
        JobApplication jobApplication = new JobApplication(dateSubmitted, applicant);

        assertNotNull(jobApplication);
        assertNull(jobApplication.getId());
        assertEquals(dateSubmitted, jobApplication.getDateSubmitted());
        assertEquals(JobApplicationStatus.DRAFT, jobApplication.getStatus());
    }

    @Test
    public void testEquals() {
        Applicant applicant = mock(Applicant.class);
        LocalDate dateSubmitted = LocalDate.now();
        JobApplication jobApplication1 = new JobApplication(dateSubmitted, applicant, JobApplicationStatus.SUBMITTED);
        JobApplication jobApplication2 = new JobApplication(dateSubmitted, applicant, JobApplicationStatus.SUBMITTED);
        JobApplication jobApplication3 = new JobApplication(dateSubmitted, applicant, JobApplicationStatus.SUBMITTED);
        JobApplication jobApplication4 = new JobApplication(dateSubmitted, applicant, JobApplicationStatus.CANCELED);

        assertFalse(jobApplication1.equals(null));
        assertFalse(jobApplication1.equals(true));

        assertTrue(jobApplication1.equals(jobApplication1));

        assertTrue(jobApplication1.equals(jobApplication2));
        assertTrue(jobApplication2.equals(jobApplication3));
        assertTrue(jobApplication1.equals(jobApplication3));

        assertTrue(jobApplication1.equals(jobApplication2));
        assertTrue(jobApplication2.equals(jobApplication1));

        assertFalse(jobApplication1.equals(jobApplication4));

        assertEquals(jobApplication1.hashCode(), jobApplication2.hashCode());
    }
}
