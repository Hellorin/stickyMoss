package com.hellorin.stickyMoss.facade.mappers;


import com.hellorin.stickyMoss.documents.domain.DocumentFileFormat;
import com.hellorin.stickyMoss.documents.dtos.CVDTO;

import com.hellorin.stickyMoss.documents.dtos.DocumentFileFormatDTO;
import com.hellorin.stickyMoss.jobHunting.domain.*;
import com.hellorin.stickyMoss.documents.domain.CV;
import com.hellorin.stickyMoss.jobHunting.dtos.*;
import ma.glasnost.orika.*;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

/**
 * Created by hellorin on 26/05/17.
 */
@Component
public class StickyMossOrikaMapper {
    private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    public StickyMossOrikaMapper() throws NoSuchAlgorithmException {
        mapperFactory.classMap(JobApplication.class, JobApplicationDTO.class)
                .byDefault()
                .register();

        mapperFactory.classMap(JobApplicationStatus.class, JobApplicationStatusDTO.class)
                .byDefault()
                .register();

        mapperFactory.classMap(JobOffer.class, JobOfferDTO.class)
                .byDefault()
                .register();

        mapperFactory.classMap(JobOfferStatus.class, JobOfferStatusDTO.class)
                .byDefault()
                .register();

        mapperFactory.classMap(DocumentFileFormat.class, DocumentFileFormatDTO.class)
                .byDefault()
                .register();

        mapperFactory.classMap(CV.class, CVDTO.class)
                .byDefault()
                .register();

        mapperFactory.classMap(ApplicantDTO.class, Applicant.class)
                .field("pwd", "encPassword")
                .byDefault()
                .register();

    }

    public StickyMossMapperFacade getFacade() {
        return StickyMossMapperFacade.getInstance(mapperFactory.getMapperFacade());
    }
}
