package com.hellorin.stickyMoss.jobHunting.repositories;

import com.hellorin.stickyMoss.jobHunting.domain.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hellorin on 30.06.17.
 */
@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
}
