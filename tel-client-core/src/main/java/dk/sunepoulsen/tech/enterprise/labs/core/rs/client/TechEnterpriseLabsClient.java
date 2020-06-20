package dk.sunepoulsen.tech.enterprise.labs.core.rs.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.exceptions.ClientBadRequestException;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.exceptions.ClientConflictException;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.exceptions.ClientInternalServerException;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.exceptions.ClientNotFoundException;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.exceptions.ClientNotImplementedException;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.exceptions.ClientResponseException;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.generators.RequestIdGenerator;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.generators.UUIDRequestIdGenerator;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.ServiceError;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/**
 * Http Client to make calls to a Tech Enterprise Labs service.
 */
public class TechEnterpriseLabsClient {
    private URI uri;
    private HttpClient client;
    private Duration requestTimeout;

    private ObjectMapper objectMapper;
    private RequestIdGenerator requestIdGenerator;

    public TechEnterpriseLabsClient(URI uri) {
        this(uri, HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(30))
        );

        this.objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    public TechEnterpriseLabsClient(URI uri, HttpClient.Builder httpClientBuilder) {
        this.uri = uri;
        this.client = httpClientBuilder.build();
        this.requestTimeout = Duration.ofSeconds(30);

        this.requestIdGenerator = new UUIDRequestIdGenerator();
    }

    public <T> CompletableFuture<T> get(String url, Class<T> clazz) {
        return executeRequest("GET", url, clazz);
    }

    public <T, R> CompletableFuture<R> post(String url, T bodyValue, Class<R> clazzResult) {
        return executeRequest("POST", url, bodyValue, clazzResult);
    }

    public <T, R> CompletableFuture<R> patch(String url, T bodyValue, Class<R> clazzResult) {
        return executeRequest("PATCH", url, bodyValue, clazzResult);
    }

    public CompletableFuture<String> delete(String url) {
        return executeRequest("DELETE", url);
    }

    private CompletableFuture<String> executeRequest(String method, String url) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
            .method(method, HttpRequest.BodyPublishers.noBody())
            .uri(uri.resolve(url))
            .header("X-Request-ID", requestIdGenerator.generateId())
            .timeout(requestTimeout)
            .build();

        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(this::verifyResponseAndExtractBody);
    }

    private <T, R> CompletableFuture<R> executeRequest(String method, String url, Class<R> clazzResult) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
            .method(method, HttpRequest.BodyPublishers.noBody())
            .uri(uri.resolve(url))
            .header("X-Request-ID", requestIdGenerator.generateId())
            .timeout(requestTimeout)
            .build();

        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(this::verifyResponseAndExtractBody)
            .thenApply(s -> decodeJson(s, clazzResult));
    }

    private <T, R> CompletableFuture<R> executeRequest(String method, String url, T bodyValue, Class<R> clazzResult) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
            .method(method, HttpRequest.BodyPublishers.ofString(encodeJson(bodyValue)))
            .uri(uri.resolve(url))
            .header("Content-Type", "application/json")
            .header("X-Request-ID", requestIdGenerator.generateId())
            .timeout(requestTimeout)
            .build();

        return client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(this::verifyResponseAndExtractBody)
            .thenApply(s -> decodeJson(s, clazzResult));
    }

    private String verifyResponseAndExtractBody(HttpResponse<String> response) {
        if( response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        }

        switch(response.statusCode()) {
            case 400:
                throw new ClientBadRequestException(response, decodeJson(response.body(), ServiceError.class));

            case 404:
                throw new ClientNotFoundException(response, decodeJson(response.body(), ServiceError.class));

            case 409:
                throw new ClientConflictException(response, decodeJson(response.body(), ServiceError.class));

            case 500:
                throw new ClientInternalServerException(response, decodeJson(response.body(), ServiceError.class));

            case 501:
                throw new ClientNotImplementedException(response, decodeJson(response.body(), ServiceError.class));

            default:
                throw new ClientResponseException(response, decodeJson(response.body(), ServiceError.class));
        }
    }

    private <T> String encodeJson(T value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private <T> T decodeJson(String s, Class<T> clazz) {
        try {
            return objectMapper.readValue(s, clazz);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
