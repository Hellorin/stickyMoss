package com.hellorin.stickyMoss.facade.jobHunting.controllers;

import com.hellorin.stickyMoss.facade.mappers.StickyMossMapperFacade;
import com.hellorin.stickyMoss.facade.mappers.StickyMossOrikaMapper;
import com.hellorin.stickyMoss.jobHunting.domain.JobOffer;
import com.hellorin.stickyMoss.jobHunting.dtos.JobOfferDTO;
import com.hellorin.stickyMoss.jobHunting.services.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;

/**
 * Created by hellorin on 14.10.17.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping
@interface JobOfferRestFacadeController {
    @AliasFor(annotation = RequestMapping.class)
    String[] value () default {"api/stickyMoss/jobOffer"};
}

@JobOfferRestFacadeController
public class JobOfferRestFacade {
    @Autowired
    private JobOfferService jobOfferService;

    @Autowired
    private StickyMossOrikaMapper mapper;

    @GetMapping(path = "{id}")
    public ResponseEntity<JobOfferDTO> getJobOffer(@PathVariable final Long id) {
        JobOffer jobOffer = jobOfferService.getJobOffer(id);

        JobOfferDTO jobOfferDTO = mapper.getFacade().map(jobOffer, JobOfferDTO.class);

        return ResponseEntity.ok(jobOfferDTO);
    }

    @PutMapping
    public ResponseEntity<JobOfferDTO> addNewJobOffer(@RequestBody final JobOfferDTO jobOfferDTO) {
        JobOffer jobOffer = mapper.getFacade().map(jobOfferDTO, JobOffer.class);

        JobOffer jobOfferCreated = jobOfferService.newOffer(jobOffer);

        JobOfferDTO dto = mapper.getFacade().map(jobOfferCreated, JobOfferDTO.class);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<JobOfferDTO> archiveJobOffer(@PathVariable final Long id) {
        JobOffer jobOfferArchived = jobOfferService.archiveJobOffer(id);

        JobOfferDTO dto = mapper.getFacade().map(jobOfferArchived, JobOfferDTO.class);

        return ResponseEntity.ok().body(dto);
    }

}
