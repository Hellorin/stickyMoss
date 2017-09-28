package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.JobApplication;
import com.hellorin.stickyMoss.jobHunting.domain.JobApplicationStatus;
import com.hellorin.stickyMoss.jobHunting.exceptions.JobApplicationNotFoundException;
import com.hellorin.stickyMoss.jobHunting.repositories.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


/**
 * Created by hellorin on 30.06.17.
 */
@Service
@Transactional
public class JobApplicationService implements IJobApplicationService {
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Override
    public JobApplication newApplication(final Long userId, final JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }

    @Override
    public JobApplication getApplication(final Long userId, final Long id) {
        Optional<JobApplication> maybeJobApplication = Optional.ofNullable(jobApplicationRepository.getTheJobApplicationForGivenUser(id, userId));

        return maybeJobApplication
                .orElseThrow(JobApplicationNotFoundException::new);
    }

    @Override
    public JobApplication getApplication(final Long id) {
        Optional<JobApplication> maybeJobApplication = Optional.ofNullable(jobApplicationRepository.findOne(id));

        return maybeJobApplication
                .orElseThrow(JobApplicationNotFoundException::new);
    }

    @Override
    public JobApplication archiveApplication(final Long userId, final Long id, final JobApplicationStatus status) {
        Optional<JobApplication> jobApplication = Optional.ofNullable(this.getApplication(userId, id));

        jobApplication
                .ifPresent(application -> application.setStatus(status));

        return jobApplication.orElse(null);
    }

}
