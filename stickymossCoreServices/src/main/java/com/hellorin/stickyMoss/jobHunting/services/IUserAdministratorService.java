package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.user.domain.ApplicationUser;
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
    ApplicationUser addUser(@Valid ApplicationUser user);

    ApplicationUser promoteUser(@Valid Long id);

    ApplicationUser enableAccount(@Valid Long id);

    ApplicationUser disableAccount(@Valid Long id);

    Applicant addApplicant(@Valid Applicant applicant);

    void deleteApplicant(@Valid Long id);

}
