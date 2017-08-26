package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.exceptions.ApplicantNotFoundException;
import com.hellorin.stickyMoss.jobHunting.repositories.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by hellorin on 04.07.17.
 */
@Service
@Validated
public class ApplicantService implements UserDetailsService {
    static Logger logger = Logger.getLogger(ApplicantService.class.getName());

    @Autowired
    private ApplicantRepository applicantRepository;

    public Applicant addApplicant(final Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    public Applicant getApplicant(final Long id) {
        Applicant applicant = applicantRepository.findOne(id);

        if (applicant != null) {
            return applicant;
        } else {
            throw new ApplicantNotFoundException("Applicant not found with id " + id);
        }
    }

    @Transactional
    public void deleteApplicant(final Long id) {
        Applicant applicant = applicantRepository.findOne(id);

        if (applicant != null) {
            applicantRepository.delete(applicant);
        } else {
            throw new ApplicantNotFoundException("Applicant not found with id " + id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Applicant> applicant = applicantRepository.findByEmail(email);

        if (applicant.isPresent()) {
            return applicant.get();
        } else {
            throw new ApplicantNotFoundException("Cannot find user with email " + email);
        }
    }
}
