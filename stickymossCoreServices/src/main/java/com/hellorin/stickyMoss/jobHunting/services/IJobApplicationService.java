package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.JobApplication;
import com.hellorin.stickyMoss.jobHunting.domain.JobApplicationStatus;

import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by hellorin on 07.09.17.
 */
@Validated
public interface IJobApplicationService {
    JobApplication newApplication(@Valid @NotNull JobApplication jobApplication);

    JobApplication getApplication(@Valid @NotNull Long id, String username);

    JobApplication archiveApplication(@Valid @NotNull Long id, @Valid @NotNull JobApplicationStatus status);
}
