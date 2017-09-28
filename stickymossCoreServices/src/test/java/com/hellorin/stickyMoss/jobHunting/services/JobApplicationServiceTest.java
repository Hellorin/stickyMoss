package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.JobApplication;
import com.hellorin.stickyMoss.jobHunting.domain.JobApplicationStatus;
import com.hellorin.stickyMoss.jobHunting.exceptions.JobApplicationNotFoundException;
import com.hellorin.stickyMoss.jobHunting.repositories.JobApplicationRepository;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by hellorin on 01.07.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JobApplicationServiceTest {

    @TestConfiguration
    static class JobApplicationServiceTestContextConfiguration {
        @Bean
        public IJobApplicationService jobApplicationService() {
            return new JobApplicationService();
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
    private IJobApplicationService jobApplicationService;

    @MockBean
    private JobApplicationRepository jobApplicationRepository;

    @Before
    public void setup() {
        jobApplicationRepository.deleteAllInBatch();
    }

    @Test(expected = MethodConstraintViolationException.class)
    public void testNewApplicationNull() {
        jobApplicationService.newApplication(1L, null);
    }

    @Test
    public void testNewApplication() {
        // Given
        JobApplication jobApplicationBeforeSaving = mock(JobApplication.class);
        when(jobApplicationBeforeSaving.getDateSubmitted()).thenReturn(new Date());
        JobApplication jobApplicationAfterSaving = mock(JobApplication.class);
        when(jobApplicationAfterSaving.getDateSubmitted()).thenReturn(new Date());
        when(jobApplicationRepository.save(jobApplicationBeforeSaving)).thenReturn(jobApplicationAfterSaving);

        // When
        JobApplication jobApplication = jobApplicationService.newApplication(1L, jobApplicationBeforeSaving);

        // Then
        assertEquals(jobApplicationAfterSaving, jobApplication);
        verify(jobApplicationRepository, times(1)).save(jobApplicationBeforeSaving);

    }

    @Test(expected = MethodConstraintViolationException.class)
    public void testGetApplicationNull() {
        jobApplicationService.getApplication(null, null);
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

    @Test
    public void testGetApplicationForGivenUserEmail() {
        // Given
        JobApplication jobApplicationReturned = mock(JobApplication.class);
        when(jobApplicationRepository.getTheJobApplicationForGivenUser(1L, 1L)).thenReturn(jobApplicationReturned);

        // When
        JobApplication jobApplication = jobApplicationService.getApplication(1L, 1L);

        // Then
        assertEquals(jobApplicationReturned, jobApplication);
        verify(jobApplicationRepository, times(1)).getTheJobApplicationForGivenUser(1L, 1L);
    }


    @Test
    public void testArchiveApplication() {
        // Given
        JobApplication jobApplicationBefore = mock(JobApplication.class);
        when(jobApplicationRepository.getTheJobApplicationForGivenUser(1L, 1L)).thenReturn(jobApplicationBefore);

        // When
        JobApplication jobApplicationReturned = jobApplicationService.archiveApplication(1L, 1L, JobApplicationStatus.CANCELED);

        // Then
        assertNotNull(jobApplicationReturned);
        verify(jobApplicationBefore, times(1)).setStatus(JobApplicationStatus.CANCELED);
    }

    @Test(expected = JobApplicationNotFoundException.class)
    public void testArchiveApplicationNotFound() {
        // Given
        when(jobApplicationRepository.getTheJobApplicationForGivenUser(1L, 1L)).thenReturn(null);

        // When
        JobApplication jobApplicationReturned = jobApplicationService.archiveApplication(1L, 1L, JobApplicationStatus.CANCELED);
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
