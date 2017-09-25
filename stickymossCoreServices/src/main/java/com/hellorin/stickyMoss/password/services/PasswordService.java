package com.hellorin.stickyMoss.password.services;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hellorin on 17.07.17.
 */
@Service
public class PasswordService {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String encode(final String plainPassword) {
        return passwordEncoder().encode(plainPassword);
    }

    public boolean verifyPassword(final String plainPassword, final Applicant applicant) throws NoSuchAlgorithmException {
        return applicant.verifyPassword(encode(plainPassword));
    }


}
