package com.hellorin.stickyMoss.password.services;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hellorin on 17.07.17.
 */
@Service
public class PasswordService {

    @Value("${encryption.salt}")
    private String salt;

    public String encode(final String plainPassword) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            final String plainPassWithSalt = salt + plainPassword;
            byte[] passBytes = plainPassWithSalt.getBytes();
            byte[] passHash = sha256.digest(passBytes);

            return new String(passHash, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public boolean verifyPassword(final String plainPassword, final Applicant applicant) throws NoSuchAlgorithmException {
        return applicant.verifyPassword(encode(plainPassword));
    }


}
