package com.hellorin.stickyMoss.jobHunting.services;

import com.hellorin.stickyMoss.user.domain.ApplicationUser;
import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.exceptions.ApplicantNotFoundException;
import com.hellorin.stickyMoss.jobHunting.exceptions.UserNotFoundException;
import com.hellorin.stickyMoss.password.services.PasswordService;
import com.hellorin.stickyMoss.user.repositories.ApplicationUserRepository;
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
        return applicationUserRepository.save(user);
    }

    @Override
    public ApplicationUser promoteUser(final Long id) {
        return applicationUserRepository.getOne(id);
    }

    @Override
    public ApplicationUser enableAccount(final Long id) {
        Optional<ApplicationUser> opUser = Optional.ofNullable(applicationUserRepository.findOne(id));

        ApplicationUser user =
            opUser.orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        user.enableUser();

        return user;
    }

    @Override
    public ApplicationUser disableAccount(final Long id) {
        Optional<ApplicationUser> opUser = Optional.ofNullable(applicationUserRepository.findOne(id));

        ApplicationUser user =
                opUser.orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        user.disableUser();

        return user;
    }

}
