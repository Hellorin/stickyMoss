package com.hellorin.stickyMoss.facade.jobHunting.controllers;

import com.hellorin.stickyMoss.facade.jobHunting.exceptions.EntityNotFoundException;
import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.facade.mappers.StickyMossOrikaMapper;
import com.hellorin.stickyMoss.jobHunting.dtos.ApplicantDTO;
import com.hellorin.stickyMoss.jobHunting.exceptions.ApplicantNotFoundException;
import com.hellorin.stickyMoss.jobHunting.services.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;

/**
 * Created by hellorin on 04.07.17.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping
@interface ApplicantRestFacadeController {
    @AliasFor(annotation = RequestMapping.class)
    String[] value () default {"api/stickyMoss/applicant"};
}

@ApplicantRestFacadeController
public class ApplicantRestFacade {

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private StickyMossOrikaMapper mapper;

    @PutMapping( consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    private ResponseEntity<?> newApplicant(@RequestBody final ApplicantDTO applicantDTO) {
        Applicant applicant = mapper.getFacade().map(applicantDTO, Applicant.class);

        Applicant applicantCreated = applicantService.addApplicant(applicant);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(applicantCreated.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "{id}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    private ResponseEntity<ApplicantDTO> getApplicant(@PathVariable final Long id) throws EntityNotFoundException {
        try {
            Applicant returnedApplicant = applicantService.getApplicant(id);

            return ResponseEntity.ok(mapper.getFacade().map(returnedApplicant, ApplicantDTO.class));
        } catch (ApplicantNotFoundException exception) {
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    private void deleteApplicant (@PathVariable final Long id) throws EntityNotFoundException {
        try {
            applicantService.deleteApplicant(id);
        } catch (ApplicantNotFoundException exception) {
            throw new EntityNotFoundException();
        }
    }
}
