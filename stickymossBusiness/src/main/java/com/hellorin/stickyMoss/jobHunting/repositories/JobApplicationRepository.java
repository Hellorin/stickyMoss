package com.hellorin.stickyMoss.jobHunting.repositories;

import com.hellorin.stickyMoss.jobHunting.domain.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by hellorin on 30.06.17.
 */
@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    @Query("SELECT appl FROM JobApplication appl JOIN appl.applicant a WHERE appl.id = :id AND a.email = :email")
    public JobApplication getTheJobApplicationForGivenUser(@Param("id") Long id, @Param("email") String email);
}
