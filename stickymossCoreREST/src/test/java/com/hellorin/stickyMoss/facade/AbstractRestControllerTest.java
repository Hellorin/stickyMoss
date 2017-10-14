package com.hellorin.stickyMoss.facade;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by hellorin on 07/05/17.
 */
public abstract class AbstractRestControllerTest {

    @Autowired
    protected MockMvc mvc;

    protected HttpMessageConverter mappingJackson2HttpMessageConverter;

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    protected String jsonDateFormating(final LocalDate date) {
        return DateTimeFormatter.ISO_DATE.format(date);
    }

    private static String[] getAnnotationDefaultMethodValue(final Class<?> clazz, final String attribute) throws NoSuchMethodException {
        Method method = clazz.getDeclaredMethod(attribute);
        return (String[]) method.getDefaultValue();
    }

    private static String getNElemDefaultMethodValueOfArray(final int elem, final Class<?> clazz, final String attribute) throws NoSuchMethodException {
        return getAnnotationDefaultMethodValue(clazz, attribute)[elem];
    }

    public static String getRestControllerBaseURL(final Class<?> controllerClazzAnnotation) throws NoSuchMethodException {
        return getNElemDefaultMethodValueOfArray(0, controllerClazzAnnotation, "value");
    }

}
