package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;


/**
 * Created by hellorin on 06.09.17.
 */
@Validated
public interface IApplicantService extends UserDetailsService {

    Applicant getApplicant(Long id);
}
