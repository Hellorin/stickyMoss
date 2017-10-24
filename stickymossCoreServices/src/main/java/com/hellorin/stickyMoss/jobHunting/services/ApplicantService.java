package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.exceptions.ApplicantNotFoundException;
import com.hellorin.stickyMoss.jobHunting.repositories.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by hellorin on 04.07.17.
 */
@Service
@Transactional
public class ApplicantService implements IApplicantService {
    static Logger logger = Logger.getLogger(ApplicantService.class.getName());

    @Autowired
    protected ApplicantRepository applicantRepository;

    @Override
    public Applicant getApplicant(final Long id) {
        Optional<Applicant> applicant = Optional.ofNullable(applicantRepository.findOne(id));

        return applicant
                .orElseThrow(() -> new ApplicantNotFoundException("Applicant not found with id " + id));
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        Optional<Applicant> applicant = applicantRepository.findByEmail(email);

        return applicant
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with email " + email));
    }
}
