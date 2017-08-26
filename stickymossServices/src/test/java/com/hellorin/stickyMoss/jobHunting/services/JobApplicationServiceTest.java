package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.JobApplication;
import com.hellorin.stickyMoss.jobHunting.exceptions.JobApplicationNotFoundException;
import com.hellorin.stickyMoss.jobHunting.repositories.JobApplicationRepository;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by hellorin on 01.07.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JobApplicationServiceTest {

    @Configuration
    static class JobApplicationServiceTestContextConfiguration {
        @Bean
        public JobApplicationService jobApplicationService() {
            return new JobApplicationService();
        }

        @Bean
        public JobApplicationRepository jobApplicationRepository() {
            return mock(JobApplicationRepository.class);
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
    private JobApplicationService jobApplicationService;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Before
    public void setup() {
        jobApplicationRepository.deleteAllInBatch();
    }

    @Test(expected = MethodConstraintViolationException.class)
    public void testNewApplicationNull() {
        jobApplicationService.newApplication(null);
    }

    @Test
    public void testNewApplication() {
        // Given
        JobApplication jobApplicationBeforeSaving = mock(JobApplication.class);
        JobApplication jobApplicationAfterSaving = mock(JobApplication.class);
        when(jobApplicationRepository.save(jobApplicationBeforeSaving)).thenReturn(jobApplicationAfterSaving);

        // When
        JobApplication jobApplication = jobApplicationService.newApplication(jobApplicationBeforeSaving);

        // Then
        assertEquals(jobApplicationAfterSaving, jobApplication);
        verify(jobApplicationRepository, times(1)).save(jobApplicationBeforeSaving);

    }

    @Test(expected = MethodConstraintViolationException.class)
    public void testGetApplicationNull() {
        jobApplicationService.getApplication(null);
    }

    @Test
    public void testGetApplicationNormal() {
        // Given
        JobApplication jobApplicationReturned = mock(JobApplication.class);
        when(jobApplicationRepository.findOne(1L)).thenReturn(jobApplicationReturned);

        // When
        JobApplication jobApplication = jobApplicationService.getApplication(1L);

        // Then
        assertEquals(jobApplicationReturned, jobApplication);
        verify(jobApplicationRepository, times(1)).findOne(1L);
    }

    @Test(expected = JobApplicationNotFoundException.class)
    public void testGetApplicationUnknown() {
        // Given
        doReturn(null).when(jobApplicationRepository).findOne(1L);

        // When
        jobApplicationService.getApplication(1L);
    }

    /*@Test


    @Test(expected = IllegalArgumentException.class)
    public void testDeleteApplicationNull() {
        jobApplicationService.deleteApplication(null);
    }

    @Test
    public void testDeleteApplicationNormal() {
        // Given


        // When
        jobApplicationService.deleteApplication(1L);

        // Then
        verify(jobApplicationRepository, times(1)).delete(1L);
    }

    @Test(expected = Exception.class)
    public void testDeleteApplicationUnknown() {
        // Given
        doThrow(Exception.class).when(jobApplicationRepository).delete(2L);

        // When
        jobApplicationService.deleteApplication(2L);
    }*/
}
