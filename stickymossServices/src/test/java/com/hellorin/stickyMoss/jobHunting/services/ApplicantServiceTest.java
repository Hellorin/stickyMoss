package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.documents.factories.DocumentServicesFactory;
import com.hellorin.stickyMoss.jobHunting.exceptions.ApplicantNotFoundException;
import com.hellorin.stickyMoss.jobHunting.repositories.ApplicantRepository;
import com.hellorin.stickyMoss.password.services.PasswordService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by hellorin on 04.07.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@MockBean(DocumentServicesFactory.class)
public class ApplicantServiceTest {

    @TestConfiguration
    @EnableGlobalMethodSecurity(securedEnabled =  true, prePostEnabled = true)
    static class ApplicantServiceTestContextConfiguration {
        @Bean
        public IApplicantService applicantService() {
            return new ApplicantService();
        }
    }

    @Autowired
    private IApplicantService applicantService;

    @MockBean
    public ApplicantRepository applicantRepository;

    @MockBean
    public PasswordService passwordService;

    @Before
    public void setup() {
        Mockito.reset(applicantRepository);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testAddApplicantWithoutAuthentification() {
        // Given
        Applicant newApplicant = mock(Applicant.class);

        // When
        Applicant observedReturnedApplicant = applicantService.addApplicant(newApplicant);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"USER"})
    public void testAddApplicantWithUnauthorizedRole() {
        // Given
        Applicant newApplicant = mock(Applicant.class);

        // When
        Applicant observedReturnedApplicant = applicantService.addApplicant(newApplicant);
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testAddApplicant() {
        // Given
        Applicant newApplicant = mock(Applicant.class);
        Applicant returnedApplicant = mock(Applicant.class);

        for (Applicant applicant : Arrays.asList(newApplicant, returnedApplicant)) {
            when(applicant.getFirstname()).thenReturn("Jim");
            when(applicant.getLastname()).thenReturn("Nguyen");
            when(applicant.getEmail()).thenReturn("email@email.com");
        }
        when(newApplicant.getId()).thenReturn(null);
        when(returnedApplicant.getId()).thenReturn(1L);

        when(applicantRepository.save(newApplicant))
                .thenReturn(returnedApplicant);

        // When
        Applicant observedReturnedApplicant = applicantService.addApplicant(newApplicant);

        // Then
        assertNull(newApplicant.getId());
        assertNotNull(observedReturnedApplicant.getId());
        assertEquals(newApplicant.getFirstname(), observedReturnedApplicant.getFirstname());
        assertEquals(newApplicant.getLastname(), observedReturnedApplicant.getLastname());
        assertEquals(newApplicant.getEmail(), observedReturnedApplicant.getEmail());

        verify(applicantRepository, times(1)).save(newApplicant);
    }

    @Test
    public void testGetExistingApplicantById() {
        // Given
        Applicant desiredApplicant = mock(Applicant.class);
        when(desiredApplicant.getId()).thenReturn(1L);

        Applicant returnedApplicant = mock(Applicant.class);
        when(returnedApplicant.getId()).thenReturn(1L);
        when(returnedApplicant.getFirstname()).thenReturn("Jim");
        when(returnedApplicant.getLastname()).thenReturn("Nguyen");
        when(returnedApplicant.getEmail()).thenReturn("email@email.com");
        when(applicantRepository.findOne(1L)).thenReturn(returnedApplicant);

        // When
        Applicant observedApplicant = applicantService.getApplicant(1L);

        // Then
        assertEquals(new Long(1L), observedApplicant.getId());
        assertEquals(returnedApplicant.getLastname(), observedApplicant.getLastname());
        assertEquals(returnedApplicant.getFirstname(), observedApplicant.getFirstname());
        assertEquals(returnedApplicant.getEmail(), observedApplicant.getEmail());
        verify(applicantRepository, times(1)).findOne(1L);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"USER"})
    public void testDeleteExistingApplicantByIdWithWrongRole() {
        // When
        applicantService.deleteApplicant(1L);
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testDeleteExistingApplicantById() {
        // Given
        Applicant applicant = mock(Applicant.class);
        when(applicant.getId()).thenReturn(1L);
        when(applicantRepository.findOne(1L)).thenReturn(applicant);

        // When
        applicantService.deleteApplicant(1L);

        // Then
        verify(applicantRepository, times(1)).findOne(1L);
    }

    @Test(expected = ApplicantNotFoundException.class)
    public void testGetInexistingApplicantById() {
        // Given
        Applicant desiredApplicant = mock(Applicant.class);
        when(desiredApplicant.getId()).thenReturn(1L);

        Applicant returnedApplicant = mock(Applicant.class);
        when(returnedApplicant.getId()).thenReturn(1L);
        when(returnedApplicant.getFirstname()).thenReturn("Jim");
        when(returnedApplicant.getLastname()).thenReturn("Nguyen");
        when(returnedApplicant.getEmail()).thenReturn("email@email.com");
        when(applicantRepository.findOne(1L)).thenReturn(null);

        // When
        applicantService.getApplicant(1L);

        // Then
        verify(applicantRepository, times(1)).findOne(1L);

    }

    @Test(expected = ApplicantNotFoundException.class)
    @WithMockUser(roles={"ADMIN"})
    public void testDeleteInexistingApplicantById() {
        // Given
        Applicant applicant = mock(Applicant.class);
        when(applicant.getId()).thenReturn(1L);
        when(applicantRepository.findOne(1L)).thenReturn(null);

        // When
        applicantService.deleteApplicant(1L);
    }

}
