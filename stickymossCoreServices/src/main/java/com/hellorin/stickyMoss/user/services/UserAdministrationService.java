package com.hellorin.stickyMoss.user.services;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.exceptions.ApplicantNotFoundException;
import com.hellorin.stickyMoss.jobHunting.services.ApplicantService;
import com.hellorin.stickyMoss.password.services.PasswordService;
import com.hellorin.stickyMoss.user.domain.ApplicationUser;
import com.hellorin.stickyMoss.user.exceptions.CannotPromoteUserException;
import com.hellorin.stickyMoss.user.exceptions.UserNotFoundException;
import com.hellorin.stickyMoss.user.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by hellorin on 21.10.17.
 */
@Service
@Transactional
@Primary
public class UserAdministrationService extends ApplicantService implements IUserAdministratorService {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Override
    public Applicant addApplicant(final Applicant applicant) {
        applicant.setEncPassword(passwordService.encode(applicant.getPassword()));

        return applicantRepository.save(applicant);
    }

    @Override
    public void deleteApplicant(final Long id) {
        Optional<Applicant> applicant = Optional.ofNullable(applicantRepository.findOne(id));

        applicantRepository.delete(
                applicant.orElseThrow(() -> new ApplicantNotFoundException("Applicant not found with id " + id)));
    }

    @Override
    public ApplicationUser addUser(final ApplicationUser user) {
        user.setEncPassword(passwordService.encode(user.getPassword()));

        return applicationUserRepository.save(user);
    }

    private boolean abstractPromoteUser(final Long id,
                                        final Predicate<ApplicationUser> isAlreadyApplied,
                                        final Predicate<ApplicationUser> filter) {
        Optional<ApplicationUser> user = Optional.ofNullable(applicationUserRepository.findOne(id));

        if (user.isPresent()) {
            ApplicationUser appUser = user.filter(filter)
                .orElseThrow(() -> new CannotPromoteUserException());

            if (!isAlreadyApplied.test(appUser)) {
                appUser.promote();

                return true;
            } else {
                return false;
            }

        } else {
            throw new UserNotFoundException("User not found with id " + id);
        }
    }

    @Override
    public boolean promoteUser(final Long id) {
        return abstractPromoteUser(id,
                user -> user.hasRole(role -> role.equals("ADMIN")),
                u -> ! (u instanceof Applicant));
    }

    @Override
    public boolean forcePromoteUser(final Long id) {
        return abstractPromoteUser(id,
                user -> user.hasRole(role -> role.equals("ADMIN")),
                u -> true);
    }

    @Override
    public boolean enableAccount(final Long id) {
        Optional<ApplicationUser> opUser = Optional.ofNullable(applicationUserRepository.findOne(id));

        ApplicationUser user =
            opUser.orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        user.enableUser();

        return true;
    }

    @Override
    public boolean disableAccount(final Long id) {
        Optional<ApplicationUser> opUser = Optional.ofNullable(applicationUserRepository.findOne(id));

        ApplicationUser user =
                opUser.orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        user.disableUser();

        return true;
    }

}
