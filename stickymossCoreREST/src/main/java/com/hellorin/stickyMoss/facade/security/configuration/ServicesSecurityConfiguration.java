package com.hellorin.stickyMoss.facade.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Created by hellorin on 06.09.17.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
public class ServicesSecurityConfiguration {
}
