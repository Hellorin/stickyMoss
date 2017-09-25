package com.hellorin.stickyMoss.facade.jobHunting.controllers;

import com.hellorin.stickyMoss.TestStickyMossConfiguration;
import com.hellorin.stickyMoss.facade.AbstractRestControllerTest;
import com.hellorin.stickyMoss.facade.security.configuration.CustomWebSecurityConfigurerAdapter;
import com.hellorin.stickyMoss.facade.security.configuration.WebSecurityConfiguration;
import com.hellorin.stickyMoss.facade.validation.configuration.ValidatorsConfiguration;
import com.hellorin.stickyMoss.jobHunting.domain.Applicant;
import com.hellorin.stickyMoss.jobHunting.dtos.ApplicantDTO;
import com.hellorin.stickyMoss.jobHunting.repositories.ApplicantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hellorin on 16.07.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestStickyMossConfiguration.class,
        WebSecurityConfiguration.class, CustomWebSecurityConfigurerAdapter.class,
        ValidatorsConfiguration.class})
@AutoConfigureMockMvc
public class ApplicantRestFacadeTest extends AbstractRestControllerTest {

    private Applicant applicant;

    @Autowired
    private ApplicantRepository applicantRepository;

    private String baseUrl;

    @Before
    public void setup() throws Exception {
        applicantRepository.deleteAllInBatch();

        Applicant applicantBefore = new Applicant("Jim", "Lai", "password", "email@email.com");
        applicant = applicantRepository.save(applicantBefore);

        baseUrl = "/" + AbstractRestControllerTest.getRestControllerBaseURL(ApplicantRestFacadeController.class);
    }

    @Test
    public void testGetApplicantNotExisting() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/" + (applicant.getId() + 5000)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetApplicantExisting() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/" +(applicant.getId())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void testNewApplicant() throws Exception {
        ApplicantDTO applicantDTO = new ApplicantDTO();
        applicantDTO.setFirstname("Jim");
        applicantDTO.setLastname("Lain");
        applicantDTO.setEmail("email@email.com");
        applicantDTO.setPlainPassword("password");

        mvc.perform(
                MockMvcRequestBuilders.put(baseUrl)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json(applicantDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void testDeleteApplicantNotExisting() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.delete(baseUrl + "/" + applicant.getId()+1)
        ).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void testDeleteApplicantExisting() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.delete(baseUrl + "/" + applicant.getId())
        ).andExpect(status().isOk());
    }
}
