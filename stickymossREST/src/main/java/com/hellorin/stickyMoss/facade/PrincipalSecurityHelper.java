package com.hellorin.stickyMoss.facade;

import org.springframework.security.core.Authentication;

import java.security.Principal;

/**
 * Created by hellorin on 19.08.17.
 */
public final class PrincipalSecurityHelper {
    public static <T> T extractUserDetails(Class<T> clazz, Principal principal) {
        return clazz.cast(((Authentication) principal).getPrincipal());
    }
}
