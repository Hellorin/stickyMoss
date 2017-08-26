package com.hellorin.stickyMoss.facade.mappers;

import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.dtos.ApplicantDTO;
import com.hellorin.stickyMoss.password.services.PasswordService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Created by hellorin on 17.07.17.
 */
public class ApplicantMapper extends CustomMapper<ApplicantDTO, Applicant>{

    private final Function<String,String> passwordEncoder;

    public ApplicantMapper(final Function<String,String> passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void mapAtoB(ApplicantDTO applicantDTO, Applicant applicant, MappingContext context) {
        super.mapAtoB(applicantDTO, applicant, context);
        applicant.setFirstname(applicantDTO.getFirstname());
        applicant.setLastname(applicantDTO.getLastname());
        applicant.setEmail(applicantDTO.getEmail());

        String encPwd = passwordEncoder.apply(applicantDTO.getPlainPassword());
        applicant.setEncPassword(encPwd);
    }

    @Override
    public void mapBtoA(Applicant applicant, ApplicantDTO applicantDTO, MappingContext context) {
        super.mapBtoA(applicant, applicantDTO, context);
    }
}
