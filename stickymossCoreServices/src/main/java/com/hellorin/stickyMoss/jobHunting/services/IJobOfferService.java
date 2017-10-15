package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.JobOffer;
import com.hellorin.stickyMoss.jobHunting.domain.JobOfferStatus;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by hellorin on 14.10.17.
 */
@Validated
public interface IJobOfferService {
    JobOffer getJobOffer(@Valid @NotNull Long id);

    JobOffer newOffer(@Valid @NotNull JobOffer jobOffer);

    JobOffer archiveJobOffer(@Valid @NotNull Long id);
}
