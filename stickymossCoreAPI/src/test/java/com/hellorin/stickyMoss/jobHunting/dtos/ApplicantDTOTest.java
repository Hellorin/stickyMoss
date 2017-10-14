package com.hellorin.stickyMoss.jobHunting.dtos;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import org.springframework.boot.test.json.ObjectContent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by hellorin on 14.10.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@JsonTest
@ContextConfiguration
public class ApplicantDTOTest {

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
    private JacksonTester<ApplicantDTO> json;

    @Test
    public void testSerialize() throws Exception {
        ApplicantDTO dto = new ApplicantDTO();
        dto.setFirstname("Jim");
        dto.setLastname("Drake");
        dto.setEmail("email@email.com");

        Whitebox.setInternalState(dto, "id", 1L);

        System.out.println(json.write(dto));

        assertThat(json.write(dto)).hasJsonPathNumberValue("id");
        assertThat(json.write(dto)).extractingJsonPathNumberValue("id").isEqualTo(1);
        assertThat(json.write(dto)).hasJsonPathStringValue("lastname");
        assertThat(json.write(dto)).extractingJsonPathStringValue("lastname").isEqualTo("Drake");
        assertThat(json.write(dto)).hasJsonPathStringValue("firstname");
        assertThat(json.write(dto)).extractingJsonPathStringValue("firstname").isEqualTo("Jim");
        assertThat(json.write(dto)).hasJsonPathStringValue("email");
        assertThat(json.write(dto)).extractingJsonPathStringValue("email").isEqualTo("email@email.com");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":1, \"lastname\":\"Drake\", \"firstname\":\"Jim\", \"email\":\"email@email.com\"}";

        ObjectContent<ApplicantDTO> dto = json.parse(content);
        assertEquals(new Long(1), dto.getObject().getId());
        assertEquals("Jim", dto.getObject().getFirstname());
        assertEquals("Drake", dto.getObject().getLastname());
        assertEquals("email@email.com", dto.getObject().getEmail());
    }
}
