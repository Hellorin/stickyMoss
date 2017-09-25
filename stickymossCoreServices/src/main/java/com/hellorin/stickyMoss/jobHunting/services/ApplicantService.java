package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.exceptions.ApplicantNotFoundException;
import com.hellorin.stickyMoss.jobHunting.repositories.ApplicantRepository;
import com.hellorin.stickyMoss.password.services.PasswordService;
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
    private ApplicantRepository applicantRepository;

    @Autowired
    private PasswordService passwordService;

    @Override
    public Applicant addApplicant(final Applicant applicant) {
        applicant.setEncPassword(passwordService.encode(applicant.getPassword()));

        return applicantRepository.save(applicant);
    }

    @Override
    public Applicant getApplicant(final Long id) {
        Optional<Applicant> applicant = Optional.ofNullable(applicantRepository.findOne(id));

        return applicant.orElseThrow(() -> new ApplicantNotFoundException("Applicant not found with id " + id));
    }

    @Override
    public void deleteApplicant(final Long id) {
        Optional<Applicant> applicant = Optional.ofNullable(applicantRepository.findOne(id));

        applicantRepository.delete(
                applicant.orElseThrow(() -> new ApplicantNotFoundException("Applicant not found with id " + id)));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Applicant> applicant = applicantRepository.findByEmail(email);

        return applicant.orElseThrow(() -> new ApplicantNotFoundException("Cannot find user with email " + email));
    }
}
