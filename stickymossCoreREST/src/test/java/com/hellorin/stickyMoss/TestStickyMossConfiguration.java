package com.hellorin.stickyMoss;

import com.hellorin.stickyMoss.facade.jobHunting.controllers.ApplicantRestFacade;
import com.hellorin.stickyMoss.facade.jobHunting.controllers.JobApplicationRestFacade;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by hellorin on 24.09.17.
 */
@SpringBootApplication
@EntityScan(basePackages = {
        "com.hellorin.stickyMoss.jobHunting.domain",
        "com.hellorin.stickyMoss.documents.domain",
        "com.hellorin.stickyMoss.companies.domain",
        "com.hellorin.stickyMoss.classification.domain",
})
@EnableJpaRepositories(basePackages = {
        "com.hellorin.stickyMoss.jobHunting.repositories",
        "com.hellorin.stickyMoss.documents.repositories",
})
@EnableAsync
@EnableScheduling
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
public class TestStickyMossConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public JobApplicationRestFacade jobApplicationRestFacade() {
        return new JobApplicationRestFacade();
    }

    @Bean
    public ApplicantRestFacade applicantRestFacade() {
        return new ApplicantRestFacade();
    }


}



