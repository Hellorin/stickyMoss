package com.hellorin.stickyMoss.jobHunting.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by hellorin on 16.07.17.
 */
@RunWith(JUnit4.class)
public class ApplicantTest {
    @Test
    public void testConstructor() {
        Applicant applicant = new Applicant("Jim", "Lai", "encPwd", "email@email.com");

        assertNotNull(applicant);
        assertNull(applicant.getId());
        assertEquals("Jim", applicant.getFirstname());
        assertEquals("Lai", applicant.getLastname());
        assertEquals("email@email.com", applicant.getEmail());
        assertEquals(0, applicant.getJobApplications().size());

        applicant.setFirstname("Jimmy");
        applicant.setLastname("Lain");
        applicant.setEncPassword("encPwd2");
        applicant.setEmail("email2@email.com");
        assertEquals("Jimmy", applicant.getFirstname());
        assertEquals("Lain", applicant.getLastname());
        assertEquals("email2@email.com", applicant.getEmail());
    }

    @Test
    public void testAddApplications() {
        Applicant applicant = new Applicant("", "Lai", "encPwd", "email@email.com");
        JobApplication jobApplication = mock(JobApplication.class);
        applicant.addJobApplication(jobApplication);

        assertNotNull(applicant.getJobApplications());
        assertEquals(1, applicant.getJobApplications().size());
        assertEquals(jobApplication, applicant.getJobApplications().get(0));
    }

    @Test
    public void testEquals() {
        Applicant applicant1 = new Applicant("Jim", "Lai", "encPwd", "email@email.com");
        Applicant applicant2 = new Applicant("Jim", "Lai", "encPwd", "email@email.com");
        Applicant applicant3 = new Applicant("Jim", "Lai", "encPwd", "email@email.com");
        Applicant applicant4 = new Applicant("Jim1", "Lai", "encPwd", "email@email.com");

        assertFalse(applicant1.equals(null));
        assertFalse(applicant1.equals(true));

        assertTrue(applicant1.equals(applicant1));

        assertTrue(applicant1.equals(applicant2));
        assertTrue(applicant2.equals(applicant3));
        assertTrue(applicant1.equals(applicant3));

        assertTrue(applicant2.equals(applicant3));
        assertTrue(applicant3.equals(applicant2));

        assertFalse(applicant1.equals(applicant4));
    }
}
