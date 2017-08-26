package com.hellorin.stickyMoss.facade.mappers;


import com.hellorin.stickyMoss.documents.domain.Document;
import com.hellorin.stickyMoss.documents.domain.DocumentFileFormat;
import com.hellorin.stickyMoss.documents.dtos.CVDTO;
import com.hellorin.stickyMoss.documents.dtos.DocumentDTO;
import com.hellorin.stickyMoss.documents.dtos.DocumentFileFormatDTO;
import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.domain.JobApplication;
import com.hellorin.stickyMoss.documents.domain.CV;
import com.hellorin.stickyMoss.jobHunting.domain.JobApplicationStatus;
import com.hellorin.stickyMoss.jobHunting.dtos.ApplicantDTO;
import com.hellorin.stickyMoss.jobHunting.dtos.JobApplicationDTO;
import com.hellorin.stickyMoss.jobHunting.dtos.JobApplicationStatusDTO;
import com.hellorin.stickyMoss.password.services.PasswordService;
import ma.glasnost.orika.*;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.function.Function;

/**
 * Created by hellorin on 26/05/17.
 */
@Component
public class StickyMossOrikaMapper {
    private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Autowired
    private PasswordService passwordService;

    public StickyMossOrikaMapper() throws NoSuchAlgorithmException {
        mapperFactory.classMap(JobApplication.class, JobApplicationDTO.class).byDefault().register();

        mapperFactory.classMap(JobApplicationStatus.class, JobApplicationStatusDTO.class).byDefault().register();

        mapperFactory.classMap(DocumentFileFormat.class, DocumentFileFormatDTO.class).byDefault().register();

        mapperFactory.classMap(CV.class, CVDTO.class)
                .byDefault()
                .register();

        mapperFactory.classMap(ApplicantDTO.class, Applicant.class)
                .customize(new ApplicantMapper(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        return passwordService.encode(s);
                    }
                }))
                .register();

    }

    public MapperFacade getFacade() {
        return mapperFactory.getMapperFacade();
    }
}
