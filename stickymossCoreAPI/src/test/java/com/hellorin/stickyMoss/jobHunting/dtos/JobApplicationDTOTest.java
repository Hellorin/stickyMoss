package com.hellorin.stickyMoss.jobHunting.dtos;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.hellorin.stickyMoss.documents.dtos.CVDTO;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by hellorin on 14.10.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@JsonTest
@ContextConfiguration
public class JobApplicationDTOTest {

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
    private JacksonTester<JobApplicationDTO> json;

    @Test
    public void testSerialize() throws Exception {
        CVDTO cvdto = new CVDTO();
        cvdto.setId(1L);

        LocalDate now = LocalDate.now();
        JobApplicationDTO dto = new JobApplicationDTO();

        dto.setId(1L);
        dto.setDateSubmitted(now);
        dto.setStatus(JobApplicationStatusDTO.CANCELED);
        dto.setCv(cvdto);

        assertThat(json.write(dto)).hasJsonPathNumberValue("id");
        assertThat(json.write(dto)).extractingJsonPathNumberValue("id").isEqualTo(1);
        assertThat(json.write(dto)).hasJsonPathStringValue("status");
        assertThat(json.write(dto)).extractingJsonPathStringValue("status").isEqualTo("CANCELED");
        assertThat(json.write(dto)).hasJsonPathStringValue("dateSubmitted");
        assertThat(json.write(dto)).extractingJsonPathStringValue("dateSubmitted").isEqualTo("2017-10-14");
        assertThat(json.write(dto)).hasJsonPathValue("cv");
        assertThat(json.write(dto)).doesNotHaveEmptyJsonPathValue("cv");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":1,\"dateSubmitted\":\"2017-10-14\",\"cv\":{\"type\":\"cv\",\"id\":1,\"name\":null,\"format\":null,\"content\":null,\"dateUploaded\":null},\"status\":\"CANCELED\"}";

        JobApplicationDTO dto = json.parse(content).getObject();
        assertEquals(new Long(1), dto.getId());
        assertEquals(LocalDate.of(2017, 10, 14), dto.getDateSubmitted());
        assertEquals(JobApplicationStatusDTO.CANCELED, dto.getStatus());
        assertNotNull(dto.getCv());
    }

}
