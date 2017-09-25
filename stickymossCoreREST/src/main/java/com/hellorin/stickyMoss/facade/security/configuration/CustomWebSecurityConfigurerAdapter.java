package com.hellorin.stickyMoss.facade.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by hellorin on 19.08.17.
 */
@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/stickyMoss/jobApplication/*").authenticated()
                .antMatchers("/api/stickyMoss/applicant/*").permitAll()
                .antMatchers("/api/stickyMoss/documents/*").permitAll()
                .and()
                .httpBasic()
                .and().csrf().disable();
    }
}
