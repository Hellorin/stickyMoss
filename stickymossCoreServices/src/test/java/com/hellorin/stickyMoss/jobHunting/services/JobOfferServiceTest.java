package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.JobOffer;
import com.hellorin.stickyMoss.jobHunting.exceptions.JobOfferNotFoundException;
import com.hellorin.stickyMoss.jobHunting.repositories.JobOfferRepository;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Created by hellorin on 15.10.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JobOfferServiceTest {

    @TestConfiguration
    static class JobOfferServiceTestContextConfiguration {
        @Bean
        public IJobOfferService jobOfferService() {
            return new JobOfferService();
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
    private IJobOfferService jobOfferService;

    @MockBean
    private JobOfferRepository jobOfferRepository;

    @Test
    public void getJobOffer() {
        JobOffer jobOffer = mock(JobOffer.class);

        when(jobOfferRepository.findOne(1L)).thenReturn(jobOffer);

        JobOffer jobOfferReturned = jobOfferService.getJobOffer(1L);

        assertEquals(jobOffer, jobOfferReturned);
    }

    @Test(expected = JobOfferNotFoundException.class)
    public void getUnknownJobOffer() {
        JobOffer jobOffer = mock(JobOffer.class);

        when(jobOfferRepository.findOne(1L)).thenReturn(null);

        jobOfferService.getJobOffer(1L);
    }

    @Test
    public void newJobOffer() {
        JobOffer jobOfferToCreate = mock(JobOffer.class);
        JobOffer jobOfferReturned = mock(JobOffer.class);
        when(jobOfferRepository.save(jobOfferToCreate)).thenReturn(jobOfferReturned);

        JobOffer jobOffer = jobOfferService.newOffer(jobOfferToCreate);

        assertEquals(jobOfferReturned, jobOffer);
        Mockito.verify(jobOfferRepository, times(1)).save(jobOfferToCreate);
    }

    @Test(expected = MethodConstraintViolationException.class)
    public void newNullJobOffer() {
        jobOfferService.newOffer(null);
    }

    @Test(expected = MethodConstraintViolationException.class)
    public void archiveNullJobOffer() {
        jobOfferService.archiveJobOffer(null);
    }

    @Test(expected = JobOfferNotFoundException.class)
    public void archiveUnknownJobOffer() {
        when(jobOfferRepository.findOne(1L)).thenReturn(null);

        jobOfferService.archiveJobOffer(1L);
    }

    @Test
    public void archiveJobOffer() {
        JobOffer jobOffer = mock(JobOffer.class);
        when(jobOfferRepository.findOne(1L)).thenReturn(jobOffer);

        JobOffer jobOfferReturned = jobOfferService.archiveJobOffer(1L);

        assertEquals(jobOffer, jobOfferReturned);

        Mockito.verify(jobOfferRepository, times(1)).findOne(1L);
        Mockito.verify(jobOffer, times(1)).close();

    }
}
