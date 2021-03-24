package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper {
    private final ObjectMapper objectMapper;

    public JsonMapper() {
        this(springBootObjectMapper());
    }

    public JsonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String encode(Object value) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(value);
    }

    public <T> T decode(String json, Class<T> clazz) throws JsonProcessingException {
        return this.objectMapper.readValue(json, clazz);
    }

    public static String encodeAsJson(Object value) throws JsonProcessingException {
        return new JsonMapper().encode(value);
    }

    public static <T> T decodeJson(String json, Class<T> clazz) throws JsonProcessingException {
        return new JsonMapper().decode(json, clazz);
    }

    public static ObjectMapper springBootObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }
}
