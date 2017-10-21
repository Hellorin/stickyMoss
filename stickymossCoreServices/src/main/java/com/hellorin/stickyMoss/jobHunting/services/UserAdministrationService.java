package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.exceptions.ApplicantNotFoundException;
import com.hellorin.stickyMoss.password.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by hellorin on 21.10.17.
 */
@Service
@Transactional
@Primary

public class UserAdministrationService extends ApplicantService implements IUserAdministratorService {

    @Autowired
    private PasswordService passwordService;

    @Override
    public Applicant addApplicant(Applicant applicant) {
        applicant.setEncPassword(passwordService.encode(applicant.getPassword()));

        return applicantRepository.save(applicant);
    }

    @Override
    public void deleteApplicant(Long id) {
        Optional<Applicant> applicant = Optional.ofNullable(applicantRepository.findOne(id));

        applicantRepository.delete(
                applicant.orElseThrow(() -> new ApplicantNotFoundException("Applicant not found with id " + id)));
    }
}
