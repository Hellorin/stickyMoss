package com.hellorin.stickyMoss.facade.jobHunting.controllers;

import com.hellorin.stickyMoss.StickyMossDTO;
import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.domain.JobApplication;
import com.hellorin.stickyMoss.facade.mappers.StickyMossOrikaMapper;
import com.hellorin.stickyMoss.jobHunting.domain.JobApplicationStatus;
import com.hellorin.stickyMoss.jobHunting.dtos.JobApplicationDTO;
import com.hellorin.stickyMoss.jobHunting.dtos.JobApplicationStatusDTO;
import com.hellorin.stickyMoss.jobHunting.services.IApplicantService;
import com.hellorin.stickyMoss.jobHunting.services.IJobApplicationService;
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
    private IJobApplicationService jobApplicationService;

    @Autowired
    private IApplicantService applicantService;

    @Autowired
    private StickyMossOrikaMapper stickyMossOrikaMapper;

    @GetMapping(path = "{id}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<JobApplicationDTO> getJobApplication (@AuthenticationPrincipal final Applicant applicant,
                                                                @PathVariable final Long id) {
        JobApplication jobApplication = jobApplicationService.getApplication(applicant.getId(), id);

        return ResponseEntity.ok(stickyMossOrikaMapper.getFacade().map(jobApplication, JobApplicationDTO.class));
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public ResponseEntity<?> newJobApplication(@AuthenticationPrincipal final Applicant applicant,
                                               @RequestBody final JobApplicationDTO jobApplicationDTO) {
        JobApplication jobApplication = stickyMossOrikaMapper.getFacade().map(jobApplicationDTO, JobApplication.class);

        jobApplication.setApplicant(applicant);
        JobApplication jobApplicationCreated = jobApplicationService.newApplication(applicant.getId(), jobApplication);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(jobApplicationCreated.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "{id}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public ResponseEntity<JobApplicationDTO> archiveJobApplication(@AuthenticationPrincipal final Applicant applicant,
                                                                   @PathVariable final Long id,
                                                                   @RequestParam(value="type") final JobApplicationStatusDTO archivingMode) {
        JobApplicationStatus status = stickyMossOrikaMapper.getFacade().map(archivingMode, JobApplicationStatus.class);

        JobApplication archivedJobApplication = jobApplicationService.archiveApplication(applicant.getId(), id, status);

        JobApplicationDTO dto = stickyMossOrikaMapper.getFacade().map(archivedJobApplication, JobApplicationDTO.class);

        return ResponseEntity.ok(dto);
    }

}
