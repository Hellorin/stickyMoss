package com.hellorin.stickyMoss;

import com.hellorin.stickyMoss.facade.jobHunting.controllers.ApplicantRestFacade;
import com.hellorin.stickyMoss.facade.jobHunting.controllers.JobApplicationRestFacade;
import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.repositories.ApplicantRepository;
import com.hellorin.stickyMoss.jobHunting.services.ApplicantService;
import ma.glasnost.orika.CustomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by hellorin on 21.06.17.
 */
@SpringBootApplication
@EntityScan(basePackages = {
        "com.hellorin.stickyMoss.jobHunting.domain",
        "com.hellorin.stickyMoss.documents.domain",
        "com.hellorin.stickyMoss.companies.domain",
        "com.hellorin.stickyMoss.classification.domain",
        "com.hellorin.stickyMoss.translation.domain",
})
@EnableJpaRepositories(basePackages = {
        "com.hellorin.stickyMoss.jobHunting.repositories",
        "com.hellorin.stickyMoss.documents.repositories",
        "com.hellorin.stickyMoss.translation.repositories"
})
@EnableAsync
@EnableScheduling
@EnableWebMvc
@Configuration
public class StickyMossApplication extends WebMvcConfigurerAdapter {

    @Bean
    public JobApplicationRestFacade jobApplicationRestFacade() {
        return new JobApplicationRestFacade();
    }

    @Bean
    public ApplicantRestFacade applicantRestFacade() {
        return new ApplicantRestFacade();
    }

    public static void main(String[] args) {
        SpringApplication.run(StickyMossApplication.class, args);
    }

}



