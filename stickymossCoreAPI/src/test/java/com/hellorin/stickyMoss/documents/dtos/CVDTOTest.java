package com.hellorin.stickyMoss.documents.dtos;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.hellorin.stickyMoss.jobHunting.dtos.ApplicantDTO;
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
import java.time.format.DateTimeFormatter;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by hellorin on 14.10.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@JsonTest
@ContextConfiguration
public class CVDTOTest {

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
    private JacksonTester<CVDTO> json;

    @Test
    public void testSerialize() throws Exception {
        LocalDate date = LocalDate.now();

        CVDTO dto = new CVDTO();
        dto.setId(1L);
        dto.setName("filename.pdf");
        dto.setFormat(DocumentFileFormatDTO.PDF);
        dto.setContent(new byte[]{12});
        dto.setDateUploaded(date);

        assertThat(json.write(dto)).hasJsonPathNumberValue("id");
        assertThat(json.write(dto)).extractingJsonPathNumberValue("id").isEqualTo(1);
        assertThat(json.write(dto)).hasJsonPathStringValue("name");
        assertThat(json.write(dto)).extractingJsonPathStringValue("name").isEqualTo("filename.pdf");
        assertThat(json.write(dto)).hasJsonPathStringValue("format");
        assertThat(json.write(dto)).extractingJsonPathStringValue("format").isEqualTo("PDF");
        assertThat(json.write(dto)).hasJsonPathStringValue("type");
        assertThat(json.write(dto)).extractingJsonPathStringValue("type").isEqualTo("cv");
        assertThat(json.write(dto)).hasJsonPathStringValue("dateUploaded");
        assertThat(json.write(dto)).extractingJsonPathStringValue("dateUploaded").isEqualTo(DateTimeFormatter.ISO_DATE.format(date));

        System.out.println(json.write(dto));
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"type\":\"cv\",\"id\":1,\"name\":\"filename.pdf\",\"format\":\"PDF\",\"content\":\"DA==\",\"dateUploaded\":\"2017-10-14\"}";

        DocumentDTO dto = json.parse(content).getObject();

        assertTrue(dto instanceof CVDTO);
        CVDTO cvDto = (CVDTO) dto;

        assertEquals(new Long(1), cvDto.getId());
        assertEquals(1, cvDto.getContent().length);
        assertEquals(12, cvDto.getContent()[0]);
        assertEquals(LocalDate.parse("2017-10-14", DateTimeFormatter.ISO_DATE), cvDto.getDateUploaded());
        assertEquals("filename.pdf", cvDto.getFilename());
        assertEquals(DocumentFileFormatDTO.PDF, cvDto.getFormat());
    }
}
