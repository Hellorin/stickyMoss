package com.hellorin.stickyMoss.password.services;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

/**
 * Created by hellorin on 06.08.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PasswordServiceTest {
    @Configuration
    static class PasswordServiceTestContextConfiguration {
        @Bean
        public PasswordService passwordService() {
            return new PasswordService();
        }

        @Bean
        public PasswordEncoder encoder() {return new BCryptPasswordEncoder(); }
    }

    @Autowired
    private PasswordService passwordService;

    @Test
    public void test() throws NoSuchAlgorithmException {
        String encoded = passwordService.encode("Password");
        Applicant applicant = new Applicant("Jim", "Favre", encoded, "email");

        assertNotNull(encoded);
        assertNotEquals("Password", encoded);
    }
}
