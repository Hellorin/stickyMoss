package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * Created by hellorin on 21.10.17.
 */
@Validated
@Secured("ROLE_ADMIN")
public interface IUserAdministratorService {
    default void addUser() { throw new UnsupportedOperationException(); }

    default void promoteUser() { throw new UnsupportedOperationException(); }

    Applicant addApplicant(@Valid Applicant applicant);

    void deleteApplicant(@Valid Long id);
}
