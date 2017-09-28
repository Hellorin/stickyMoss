package com.hellorin.stickyMoss.password.services;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

}
