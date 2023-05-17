package uy.com.andi.reproducer.application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import jakarta.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.quarkus.jackson.ObjectMapperCustomizer;

@Singleton
public class MyObjectMapperCustomizer implements ObjectMapperCustomizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyObjectMapperCustomizer.class);

    @Override
    @SuppressWarnings({ "squid:S1874", "deprecation" }) // See https://github.com/quarkusio/quarkus/issues/23645
    public void customize(ObjectMapper objectMapper) {
        // To suppress serializing properties with null values
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        objectMapper.registerModule(new JavaTimeModule());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("customize() - enable feature {} ", MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(dateFormat);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
