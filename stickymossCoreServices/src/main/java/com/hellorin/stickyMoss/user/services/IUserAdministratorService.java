package com.hellorin.stickyMoss.user.services;

import com.hellorin.stickyMoss.user.domain.ApplicationUser;
import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by hellorin on 21.10.17.
 */
@Validated
@Secured("ROLE_ADMIN")
public interface IUserAdministratorService {
    ApplicationUser addUser(@Valid ApplicationUser user);

    boolean promoteUser(@Valid @NotNull Long id);

    boolean forcePromoteUser(@Valid @NotNull Long id);

    boolean enableAccount(@Valid @NotNull Long id);

    boolean disableAccount(@Valid @NotNull Long id);

    Applicant addApplicant(@Valid Applicant applicant);

    void deleteApplicant(@Valid @NotNull Long id);
}
