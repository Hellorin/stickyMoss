package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.JobOffer;
import com.hellorin.stickyMoss.jobHunting.exceptions.JobOfferNotFoundException;
import com.hellorin.stickyMoss.jobHunting.repositories.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by hellorin on 14.10.17.
 */
@Service
@Transactional
public class JobOfferService implements IJobOfferService {
    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Override
    public JobOffer getJobOffer(final Long id) {
        return Optional.ofNullable(jobOfferRepository.findOne(id))
                .orElseThrow(() -> new JobOfferNotFoundException());
    }

    @Override
    public JobOffer newOffer(final JobOffer jobOffer) {
        return jobOfferRepository.save(jobOffer);
    }

    @Override
    public JobOffer archiveJobOffer(final Long id) {
        return Optional.ofNullable(jobOfferRepository.findOne(id))
                .map(jobOffer -> {
                    jobOffer.close();
                    return jobOffer;
                }).orElseThrow(() -> new JobOfferNotFoundException());
    }
}
