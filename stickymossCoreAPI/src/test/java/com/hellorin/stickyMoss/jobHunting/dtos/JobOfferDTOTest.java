package com.hellorin.stickyMoss.jobHunting.dtos;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by hellorin on 14.10.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@JsonTest
@ContextConfiguration
public class JobOfferDTOTest {
    @Configuration
    static class testConfiguration {
        @Bean
        public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
            return new Jackson2ObjectMapperBuilder()
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .findModulesViaServiceLoader(true);
        }
    }

    @Autowired
    private JacksonTester<JobOfferDTO> json;

    @Test
    public void testSerialize() throws Exception {
        LocalDate date = LocalDate.now();
        JobOfferDTO dto = new JobOfferDTO();
        dto.setId(1L);
        dto.setDateDiscovered(date);
        dto.setStatus(JobOfferStatusDTO.CLOSE);

        assertThat(json.write(dto)).hasJsonPathNumberValue("id");
        assertThat(json.write(dto)).extractingJsonPathNumberValue("id").isEqualTo(1);
        assertThat(json.write(dto)).hasJsonPathStringValue("dateDiscovered");
        assertThat(json.write(dto)).extractingJsonPathStringValue("dateDiscovered").isEqualTo("2017-10-15");
        assertThat(json.write(dto)).hasJsonPathStringValue("status");
        assertThat(json.write(dto)).extractingJsonPathStringValue("status").isEqualTo("CLOSE");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":1,\"dateDiscovered\":\"2017-10-15\",\"status\":\"CLOSE\"}";
        LocalDate date = LocalDate.of(2017, 10, 15);
        JobOfferDTO dto = json.parse(content).getObject();

        assertEquals(new Long(1), dto.getId());
        assertEquals(JobOfferStatusDTO.CLOSE, dto.getStatus());
        assertEquals(date, dto.getDateDiscovered());
    }
}
