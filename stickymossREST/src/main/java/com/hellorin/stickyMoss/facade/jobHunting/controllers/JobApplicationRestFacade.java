package com.hellorin.stickyMoss.facade.jobHunting.controllers;

import com.hellorin.stickyMoss.facade.PrincipalSecurityHelper;
import com.hellorin.stickyMoss.facade.jobHunting.exceptions.EntityNotFoundException;
import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.domain.JobApplication;
import com.hellorin.stickyMoss.facade.mappers.StickyMossOrikaMapper;
import com.hellorin.stickyMoss.jobHunting.domain.JobApplicationStatus;
import com.hellorin.stickyMoss.jobHunting.dtos.JobApplicationDTO;
import com.hellorin.stickyMoss.jobHunting.dtos.JobApplicationStatusDTO;
import com.hellorin.stickyMoss.jobHunting.exceptions.ApplicantNotFoundException;
import com.hellorin.stickyMoss.jobHunting.exceptions.JobApplicationNotFoundException;
import com.hellorin.stickyMoss.jobHunting.services.ApplicantService;
import com.hellorin.stickyMoss.jobHunting.services.JobApplicationService;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;
import java.security.Principal;


/**
 * Created by hellorin on 30.06.17.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping
@interface JobApplicationRestFacadeController {
    @AliasFor(annotation = RequestMapping.class)
    String[] value () default {"api/stickyMoss/jobApplication"};
}


@JobApplicationRestFacadeController
public class JobApplicationRestFacade {

    @Autowired
    private JobApplicationService jobApplicationService;

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private StickyMossOrikaMapper stickyMossOrikaMapper;

    @GetMapping(path = "{id}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<JobApplicationDTO> getJobApplication (@AuthenticationPrincipal Applicant applicant, @PathVariable final Long id) throws EntityNotFoundException {
        try {
            JobApplication jobApplication = jobApplicationService.getApplication(id);

            return ResponseEntity.ok(stickyMossOrikaMapper.getFacade().map(jobApplication, JobApplicationDTO.class));

        } catch (JobApplicationNotFoundException exception) {
            throw new EntityNotFoundException();
        }
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public ResponseEntity<?> newJobApplication(@AuthenticationPrincipal Applicant applicant, @RequestBody final JobApplicationDTO jobApplicationDTO) throws EntityNotFoundException {
        try {
            JobApplication jobApplication;
            try {
                jobApplication = stickyMossOrikaMapper.getFacade().map(jobApplicationDTO, JobApplication.class);
            } catch (NullPointerException exp){
                return ResponseEntity.badRequest().build();
            }

            jobApplication.setApplicant(applicant);

            JobApplication jobApplicationCreated = jobApplicationService.newApplication(jobApplication);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(jobApplicationCreated.getId()).toUri();

            JobApplicationDTO dto = stickyMossOrikaMapper.getFacade().map(jobApplicationCreated, JobApplicationDTO.class);

            return ResponseEntity.created(location).build();

        } catch (MethodConstraintViolationException excp1) {
            return ResponseEntity.badRequest().build();
        } catch (ApplicantNotFoundException excp2) {
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping(path = "{id}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public ResponseEntity<JobApplicationDTO> archiveJobApplication(Principal principal, @PathVariable final Long id,
                                                                   @RequestParam(value="type") final JobApplicationStatusDTO archivingMode)
            throws EntityNotFoundException {
        Applicant applicant = PrincipalSecurityHelper.extractUserDetails(Applicant.class, principal);

        JobApplicationStatus status;
        try {
             status = stickyMossOrikaMapper.getFacade().map(archivingMode, JobApplicationStatus.class);
        } catch (NullPointerException excp) {
            return ResponseEntity.badRequest().build();
        }

        try {
            JobApplication archivedJobApplication = jobApplicationService.archiveApplication(id, status);

            return ResponseEntity.ok(stickyMossOrikaMapper.getFacade().map(archivedJobApplication, JobApplicationDTO.class));
        } catch (JobApplicationNotFoundException exception) {
            throw new EntityNotFoundException();
        }

    }

}
