package com.hellorin.stickyMoss.user.services;

import com.hellorin.stickyMoss.documents.factories.DocumentServicesFactory;
import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.exceptions.ApplicantNotFoundException;
import com.hellorin.stickyMoss.jobHunting.repositories.ApplicantRepository;
import com.hellorin.stickyMoss.password.services.PasswordService;
import com.hellorin.stickyMoss.user.domain.ApplicationUser;
import com.hellorin.stickyMoss.user.exceptions.CannotPromoteUserException;
import com.hellorin.stickyMoss.user.exceptions.UserNotFoundException;
import com.hellorin.stickyMoss.user.repositories.ApplicationUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by hellorin on 22.10.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@MockBean(DocumentServicesFactory.class)
public class UserAdministrationServiceTest {

    @TestConfiguration
    @EnableGlobalMethodSecurity(securedEnabled =  true, prePostEnabled = true)
    static class UserAdministrationServiceTestContextConfiguration {
        @Bean
        public IUserAdministratorService userAdministratorService() {
            return new UserAdministrationService();
        }
    }

    @Autowired
    private IUserAdministratorService userAdministratorService;

    @MockBean
    public ApplicantRepository applicantRepository;

    @MockBean
    public ApplicationUserRepository applicationUserRepository;

    @MockBean
    public PasswordService passwordService;

    @Before
    public void setup() {
        Mockito.reset(applicantRepository);
        Mockito.reset(applicationUserRepository);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"USER"})
    public void testDeleteExistingApplicantByIdWithWrongRole() {
        // When
        userAdministratorService.deleteApplicant(1L);
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testDeleteExistingApplicantById() {
        // Given
        Applicant applicant = mock(Applicant.class);
        when(applicant.getId()).thenReturn(1L);
        when(applicantRepository.findOne(1L)).thenReturn(applicant);

        // When
        userAdministratorService.deleteApplicant(1L);

        // Then
        verify(applicantRepository, times(1)).findOne(1L);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testAddApplicantWithoutAuthentification() {
        // Given
        Applicant newApplicant = mock(Applicant.class);

        // When
        Applicant observedReturnedApplicant = userAdministratorService.addApplicant(newApplicant);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"USER"})
    public void testAddApplicantWithUnauthorizedRole() {
        // Given
        Applicant newApplicant = mock(Applicant.class);

        // When
        Applicant observedReturnedApplicant = userAdministratorService.addApplicant(newApplicant);
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testAddApplicant() {
        // Given
        Applicant newApplicant = mock(Applicant.class);
        Applicant returnedApplicant = mock(Applicant.class);

        for (Applicant applicant : Arrays.asList(newApplicant, returnedApplicant)) {
            when(applicant.getFirstname()).thenReturn("Jim");
            when(applicant.getLastname()).thenReturn("Nguyen");
            when(applicant.getEmail()).thenReturn("email@email.com");
        }
        when(newApplicant.getId()).thenReturn(null);
        when(returnedApplicant.getId()).thenReturn(1L);

        when(applicantRepository.save(newApplicant))
                .thenReturn(returnedApplicant);

        // When
        Applicant observedReturnedApplicant = userAdministratorService.addApplicant(newApplicant);

        // Then
        assertNull(newApplicant.getId());
        assertNotNull(observedReturnedApplicant.getId());
        assertEquals(newApplicant.getFirstname(), observedReturnedApplicant.getFirstname());
        assertEquals(newApplicant.getLastname(), observedReturnedApplicant.getLastname());
        assertEquals(newApplicant.getEmail(), observedReturnedApplicant.getEmail());

        verify(applicantRepository, times(1)).save(newApplicant);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testAddUserWithoutAuthentification() {
        // Given
        ApplicationUser newUser = mock(ApplicationUser.class);

        // When
        userAdministratorService.addUser(newUser);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"USER"})
    public void testAddUserWithUnauthorizedRole() {
        // Given
        ApplicationUser newUser = mock(ApplicationUser.class);

        // When
        ApplicationUser observedReturnedApplicant = userAdministratorService.addUser(newUser);
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testAddUser() {
        // Given
        ApplicationUser newUser = mock(ApplicationUser.class);
        ApplicationUser returnedUser = mock(ApplicationUser.class);

        for (ApplicationUser user : Arrays.asList(newUser, returnedUser)) {
            when(user.getFirstname()).thenReturn("Jim");
            when(user.getLastname()).thenReturn("Nguyen");
            when(user.getEmail()).thenReturn("email@email.com");
        }
        when(newUser.getId()).thenReturn(null);
        when(returnedUser.getId()).thenReturn(1L);

        when(applicationUserRepository.save(newUser))
                .thenReturn(returnedUser);

        // When
        ApplicationUser observedReturnedUser = userAdministratorService.addUser(newUser);

        // Then
        assertNull(newUser.getId());
        assertNotNull(observedReturnedUser.getId());
        assertEquals(newUser.getFirstname(), observedReturnedUser.getFirstname());
        assertEquals(newUser.getLastname(), observedReturnedUser.getLastname());
        assertEquals(newUser.getEmail(), observedReturnedUser.getEmail());

        verify(applicationUserRepository, times(1)).save(newUser);
    }


    @Test(expected = ApplicantNotFoundException.class)
    @WithMockUser(roles={"ADMIN"})
    public void testDeleteInexistingApplicantById() {
        // Given
        Applicant applicant = mock(Applicant.class);
        when(applicant.getId()).thenReturn(1L);
        when(applicantRepository.findOne(1L)).thenReturn(null);

        // When
        userAdministratorService.deleteApplicant(1L);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testPromoteUserUnauthenticated() {
        userAdministratorService.promoteUser(1L);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"USER"})
    public void testPromoteUserAccessDenied() {
        userAdministratorService.promoteUser(1L);
    }

    @Test(expected = UserNotFoundException.class)
    @WithMockUser(roles={"ADMIN"})
    public void testPromoteUnknownUser() {
        // Given
        when(applicationUserRepository.findOne(1L)).thenReturn(null);

        // When
        userAdministratorService.promoteUser(1L);
    }

    @Test(expected = CannotPromoteUserException.class)
    @WithMockUser(roles={"ADMIN"})
    public void testPromoteApplicant() {
        // Given
        Applicant applicant = mock(Applicant.class);
        when(applicationUserRepository.findOne(1L)).thenReturn(applicant);

        userAdministratorService.promoteUser(1L);
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testPromoteUser() {
        // Given
        ApplicationUser user = mock(ApplicationUser.class);
        when(applicationUserRepository.findOne(1L)).thenReturn(user);
        when(user.hasRole(any())).thenReturn(false);

        // When
        userAdministratorService.promoteUser(1L);

        // Then
        verify(applicationUserRepository, times(1)).findOne(1L);
        verify(user, times(1)).promote();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testPromoteAlreadyPromotedUser() {
        // Given
        ApplicationUser user = mock(ApplicationUser.class);
        when(user.hasRole(anyObject())).thenReturn(true);

        when(applicationUserRepository.findOne(1L)).thenReturn(user);

        // When
        boolean isPromoted = userAdministratorService.promoteUser(1L);

        // Then
        assertFalse(isPromoted);
        verify(applicationUserRepository, times(1)).findOne(1L);
        verify(user, times(0)).promote();
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testForcePromoteUserUnauthenticated() {
        userAdministratorService.forcePromoteUser(1L);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"USER"})
    public void testForcePromoteUserAccessDenied() {
        userAdministratorService.forcePromoteUser(1L);
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testForcePromoteApplicant() {
        // Given
        Applicant applicant = mock(Applicant.class);
        when(applicationUserRepository.findOne(1L)).thenReturn(applicant);
        when(applicant.hasRole(any())).thenReturn(false);

        // When
        userAdministratorService.forcePromoteUser(1L);

        // Then
        verify(applicationUserRepository, times(1)).findOne(1L);
        verify(applicant, times(1)).promote();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testForcePromoteAlreadyApplicant() {
        // Given
        Applicant applicant = mock(Applicant.class);
        when(applicationUserRepository.findOne(1L)).thenReturn(applicant);
        when(applicant.hasRole(any())).thenReturn(true);

        // When
        boolean isPromoted =userAdministratorService.forcePromoteUser(1L);

        // Then
        assertFalse(isPromoted);
        verify(applicationUserRepository, times(1)).findOne(1L);
        verify(applicant, times(0)).promote();
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testForcePromoteUser() {
        // Given
        ApplicationUser user = mock(ApplicationUser.class);
        when(applicationUserRepository.findOne(1L)).thenReturn(user);
        when(user.hasRole(any())).thenReturn(false);

        // When
        userAdministratorService.forcePromoteUser(1L);

        // Then
        verify(applicationUserRepository, times(1)).findOne(1L);
        verify(user, times(1)).promote();
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testEnableAccountWithoutCredentials() {
        userAdministratorService.enableAccount(1L);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"USER"})
    public void testEnableAccountWithoutEnoughCredentials() {
        userAdministratorService.enableAccount(1L);
    }

    @Test(expected = UserNotFoundException.class)
    @WithMockUser(roles={"ADMIN"})
    public void testEnableInexistingAccount() {
        // Given
        when(applicationUserRepository.findOne(1L)).thenReturn(null);

        // When
        userAdministratorService.enableAccount(1L);
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testEnableExistingAccount() {
        // Given
        ApplicationUser user = mock(ApplicationUser.class);
        when(applicationUserRepository.findOne(1L)).thenReturn(user);

        // When
        Boolean enabled = userAdministratorService.enableAccount(1L);

        // Then
        assertTrue(enabled);
        verify(applicationUserRepository, times(1)).findOne(1L);
        verify(user, times(1)).enableUser();
    }


    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testDisableAccountWithoutCredentials() {
        userAdministratorService.disableAccount(1L);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles={"USER"})
    public void testDisableAccountWithoutEnoughCredentials() {
        userAdministratorService.disableAccount(1L);
    }

    @Test(expected = UserNotFoundException.class)
    @WithMockUser(roles={"ADMIN"})
    public void testDisableInexistingAccount() {
        // Given
        when(applicationUserRepository.findOne(1L)).thenReturn(null);

        // When
        userAdministratorService.disableAccount(1L);
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testDisableExistingAccount() {
        // Given
        ApplicationUser user = mock(ApplicationUser.class);
        when(applicationUserRepository.findOne(1L)).thenReturn(user);

        // When
        Boolean enabled = userAdministratorService.disableAccount(1L);

        // Then
        assertTrue(enabled);
        verify(applicationUserRepository, times(1)).findOne(1L);
        verify(user, times(1)).disableUser();
    }

}
