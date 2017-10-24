package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.documents.factories.DocumentServicesFactory;
import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

        @Bean
        public LocalValidatorFactoryBean validator() {
            final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
            return localValidatorFactoryBean;
        }

        @Bean
        public MethodValidationPostProcessor methodValidationPostProcessor() {
            final MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
            methodValidationPostProcessor.setValidator(validator());

            return methodValidationPostProcessor;
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

    @Test
    public void testLoadUserByUsername() {
        // Given
        Applicant applicant = mock(Applicant.class);
        when(applicantRepository.findByEmail("username")).thenReturn(Optional.of(applicant));

        // When
        UserDetails userDetails = applicantService.loadUserByUsername("username");

        // Then
        verify(applicantRepository, times(1)).findByEmail("username");
        assertNotNull(userDetails);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsernameUnknown() {
        // Given
        when(applicantRepository.findByEmail("username")).thenReturn(Optional.empty());

        // When
        applicantService.loadUserByUsername("username");
    }

}
