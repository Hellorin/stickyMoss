package com.hellorin.stickyMoss.jobHunting.repositories;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by hellorin on 04.07.17.
 */
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    Optional<Applicant> findByLastnameAndFirstname(String lastname, String firstname);

    Optional<Applicant> findByEmail(String email);
}
