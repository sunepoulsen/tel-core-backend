package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.generators;

public interface RequestIdGenerator {
    /**
     * Generates a new id that can be used in the 'X-Request-ID' header of a request.
     *
     * @return A non empty and non null string.
     */
    String generateId();
}
