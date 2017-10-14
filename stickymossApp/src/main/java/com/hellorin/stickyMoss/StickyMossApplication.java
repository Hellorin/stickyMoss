package com.hellorin.stickyMoss;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.hellorin.stickyMoss.facade.jobHunting.controllers.ApplicantRestFacade;
import com.hellorin.stickyMoss.facade.jobHunting.controllers.JobApplicationRestFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by hellorin on 21.06.17.
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
        "com.hellorin.stickyMoss.translation.repositories"
})
@EnableWebMvc
@EnableCaching
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
public class StickyMossApplication extends WebMvcConfigurerAdapter {

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .findModulesViaServiceLoader(true);
    }

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



