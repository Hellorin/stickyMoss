package com.hellorin.stickyMoss.facade.jobHunting.controllers;

import com.hellorin.stickyMoss.StickyMossApplication;
import com.hellorin.stickyMoss.facade.AbstractRestControllerTest;
import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.domain.JobApplication;
import com.hellorin.stickyMoss.jobHunting.dtos.JobApplicationDTO;
import com.hellorin.stickyMoss.jobHunting.dtos.JobApplicationStatusDTO;
import com.hellorin.stickyMoss.jobHunting.exceptions.ApplicantNotFoundException;
import com.hellorin.stickyMoss.jobHunting.repositories.ApplicantRepository;
import com.hellorin.stickyMoss.jobHunting.repositories.JobApplicationRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by hellorin on 30.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {StickyMossApplication.class})
@AutoConfigureMockMvc
public class JobApplicationRestFacadeTest extends AbstractRestControllerTest {

    private JobApplication jobApplication;

    private Applicant applicant;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    private String baseUrl;

    @Before
    public void setup() throws Exception {
        jobApplicationRepository.deleteAllInBatch();
        //applicantRepository.deleteAllInBatch();

        applicant = applicantRepository.save(new Applicant("firstname","lastname", "encPassword", "email"));

        JobApplication jobApplicationBefore = new JobApplication(new Date(), applicant);

        jobApplication = jobApplicationRepository.save(jobApplicationBefore);

        baseUrl = "/" + AbstractRestControllerTest.getRestControllerBaseURL(JobApplicationRestFacadeController.class);
    }

    @Test
    public void testGetJobApplicationUnauthorizedUser() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/" + "0"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("email@email.com")
    public void testGetJobApplicationNotExisting() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/" + "0"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("email@email.com")
    public void testGetJobApplication() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/" + jobApplication.getId())
                        .contentType(MediaType.ALL_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(jobApplication.getId().intValue())))
                .andExpect(jsonPath("$.dateSubmitted", is(jsonDateFormating(jobApplication.getDateSubmitted()))));
    }

    @Test
    @WithUserDetails("email@email.com")
    @Ignore
    public void testNewJobApplication() throws Exception {
        JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
        jobApplicationDTO.setDateSubmitted(new Date());

        mvc.perform(
                MockMvcRequestBuilders.put(baseUrl + "/" + (applicant.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(jobApplicationDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testNewJobApplicationWithUnauthorizedUser() throws Exception {
        JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
        jobApplicationDTO.setDateSubmitted(new Date());

        mvc.perform(
                MockMvcRequestBuilders.put(baseUrl + "/" + (applicant.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(jobApplicationDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("email2@email.com")
    @Ignore
    public void testNewJobApplicationWithUnknownApplicant() throws Exception {
        JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
        jobApplicationDTO.setDateSubmitted(new Date());

        mvc.perform(
                MockMvcRequestBuilders.put(baseUrl + "/" + (applicant.getId()-1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(jobApplicationDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("email@email.com")
    @Ignore
    public void testNewJobApplicationWithBadFormat() throws Exception {
        JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
        jobApplicationDTO.setDateSubmitted(null);

        mvc.perform(
                MockMvcRequestBuilders.put(baseUrl + "/" + applicant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(jobApplicationDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testArchiveJobApplicationWithUnauthorizedUser() throws Exception {
        JobApplication jobApplication = jobApplicationRepository.save(new JobApplication(new Date(), applicant));

        mvc.perform(
                MockMvcRequestBuilders.delete(baseUrl + "/" + jobApplication.getId() + "?type=CANCELED"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("email@email.com")
    public void testArchiveJobApplicationCancel() throws Exception {
        JobApplication jobApplication = jobApplicationRepository.save(new JobApplication(new Date(), applicant));

        mvc.perform(
                MockMvcRequestBuilders.delete(baseUrl + "/" + jobApplication.getId() + "?type=CANCELED"))
                .andExpect(status().isOk());

        mvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/" + jobApplication.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(jobApplication.getId().intValue())))
                .andExpect(jsonPath("$.dateSubmitted", is(jsonDateFormating(jobApplication.getDateSubmitted()))))
                .andExpect(jsonPath("$.status", is(JobApplicationStatusDTO.CANCELED.name())));
    }

    @Test
    @WithUserDetails("email@email.com")
    public void testArchiveJobApplicationClosed() throws Exception {
        JobApplication jobApplication = jobApplicationRepository.save(new JobApplication(new Date(), applicant));

        mvc.perform(
                MockMvcRequestBuilders.delete(baseUrl + "/" + jobApplication.getId() + "?type=CLOSED"))
                .andExpect(status().isOk());

        mvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/" + jobApplication.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(jobApplication.getId().intValue())))
                .andExpect(jsonPath("$.dateSubmitted", is(jsonDateFormating(jobApplication.getDateSubmitted()))))
                .andExpect(jsonPath("$.status", is(JobApplicationStatusDTO.CLOSED.name())));
    }

}
