package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.JobApplication;
import com.hellorin.stickyMoss.jobHunting.domain.JobApplicationStatus;

import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by hellorin on 07.09.17.
 */
@Validated
public interface IJobApplicationService {
    JobApplication newApplication(@Valid @NotNull Long userId, @Valid @NotNull JobApplication jobApplication);

    JobApplication setCVtoJobApplication(@Valid @NotNull Long userId, @Valid @NotNull Long cvId, @Valid @NotNull Long id);

    JobApplication getApplication(@Valid Long userId, @Valid @NotNull Long id);

    @Secured("ROLE_ADMIN")
    JobApplication getApplication(@Valid @NotNull Long id);

    JobApplication archiveApplication(@Valid @NotNull Long userId, @Valid @NotNull Long id, @Valid @NotNull JobApplicationStatus status);
}
