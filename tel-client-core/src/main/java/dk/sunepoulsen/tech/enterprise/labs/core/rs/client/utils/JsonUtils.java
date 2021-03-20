package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {
    private final ObjectMapper objectMapper;

    public JsonUtils() {
        this(defaultObjectMapper());
    }

    public JsonUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String encode(Object value) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(value);
    }

    public <T> T decode(String json, Class<T> clazz) throws JsonProcessingException {
        return this.objectMapper.readValue(json, clazz);
    }

    public static String encodeAsJson(Object value) throws JsonProcessingException {
        return new JsonUtils().encode(value);
    }

    public static <T> T decodeJson(String json, Class<T> clazz) throws JsonProcessingException {
        return new JsonUtils().decode(json, clazz);
    }

    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }
}
