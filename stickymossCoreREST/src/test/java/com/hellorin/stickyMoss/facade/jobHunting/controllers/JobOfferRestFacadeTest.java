package com.hellorin.stickyMoss.facade.jobHunting.controllers;

import com.hellorin.stickyMoss.TestStickyMossConfiguration;
import com.hellorin.stickyMoss.facade.AbstractRestControllerTest;
import com.hellorin.stickyMoss.jobHunting.domain.JobOffer;
import com.hellorin.stickyMoss.jobHunting.domain.JobOfferStatus;
import com.hellorin.stickyMoss.jobHunting.dtos.JobOfferDTO;
import com.hellorin.stickyMoss.jobHunting.dtos.JobOfferStatusDTO;
import com.hellorin.stickyMoss.jobHunting.repositories.JobOfferRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Created by hellorin on 15.10.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestStickyMossConfiguration.class})
@AutoConfigureMockMvc
public class JobOfferRestFacadeTest extends AbstractRestControllerTest {

    private String baseUrl;

    private JobOffer jobOffer;

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Before
    public void setup() throws Exception {
        jobOfferRepository.deleteAllInBatch();

        jobOffer = jobOfferRepository.save(new JobOffer(LocalDate.now(), JobOfferStatus.OPEN));

        baseUrl = "/" + AbstractRestControllerTest.getRestControllerBaseURL(JobOfferRestFacadeController.class);
    }

    @Test
    public void getJobOffer() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/" + (jobOffer.getId())))
                .andExpect(status().isOk());
    }

    @Test
    public void getUnknownJobOffer() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/" + (jobOffer.getId()-1)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void newJobOffer() throws Exception {
        JobOfferDTO dto = new JobOfferDTO();
        dto.setDateDiscovered(LocalDate.of(2017,10,10));
        dto.setStatus(JobOfferStatusDTO.OPEN);

        mvc.perform(
                MockMvcRequestBuilders.put(baseUrl).content(json(dto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void archiveJobOffer() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.patch(baseUrl + "/" + jobOffer.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void archiveUnknownJobOffer() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.patch(baseUrl + "/" + (jobOffer.getId()-1)))
                .andExpect(status().isNotFound());
    }
}
