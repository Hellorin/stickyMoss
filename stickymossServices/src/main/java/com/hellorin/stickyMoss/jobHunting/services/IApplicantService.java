package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;


/**
 * Created by hellorin on 06.09.17.
 */
@Validated
public interface IApplicantService extends UserDetailsService {

    Applicant getApplicant(Long id);

    @Secured("ROLE_ADMIN")
    Applicant addApplicant(@Valid Applicant applicant);

    @Secured("ROLE_ADMIN")
    void deleteApplicant(@Valid Long id);
}
