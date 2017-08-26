package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.JobApplication;
import com.hellorin.stickyMoss.jobHunting.domain.JobApplicationStatus;
import com.hellorin.stickyMoss.jobHunting.exceptions.JobApplicationNotFoundException;
import com.hellorin.stickyMoss.jobHunting.repositories.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Created by hellorin on 30.06.17.
 */
@Service
@Transactional
@Validated
public class JobApplicationService {
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public JobApplication newApplication(@Valid @NotNull final JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }

    public JobApplication getApplication(@Valid @NotNull final Long id) {
        Optional<JobApplication> maybeJobApplication = Optional.ofNullable(jobApplicationRepository.findOne(id));

        if (maybeJobApplication.isPresent()) {
            if (userIsAuthorized(maybeJobApplication.get())) {
                return maybeJobApplication.get();
            } else {
                return null;
            }
        } else {
            throw new JobApplicationNotFoundException();
        }
    }

    public JobApplication archiveApplication(@Valid @NotNull final Long id, @Valid @NotNull final JobApplicationStatus status) {
        JobApplication jobApplication = this.getApplication(id);

        if (userIsAuthorized(jobApplication)) {
            jobApplication.setStatus(status);

            return jobApplication;
        } else {
            return null;
        }
    }

    private boolean userIsAuthorized(final JobApplication jobApplication){
        // TODO implement it with UserDetails
        return true;
    }

}
