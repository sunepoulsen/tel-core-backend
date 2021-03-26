package dk.sunepoulsen.tech.enterprise.labs.core.component.tests.http

import com.google.common.net.MediaType
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.docker.DockerDeployment
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.verification.HttpResponseVerificator
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.utils.JsonMapper
import groovy.util.logging.Slf4j

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

@Slf4j
class HttpHelper {

    private DockerDeployment deployment
    protected HttpClient httpClient

    HttpHelper(DockerDeployment deployment) {
        this.deployment = deployment
        initHttpClient()
    }

    void initHttpClient() {
        initHttpClient(Duration.ofSeconds(30L))
    }

    void initHttpClient(Duration timeout) {
        this.httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(timeout)
            .build()
    }

    HttpRequest.Builder newRequestBuilder(String containerName, String url) {
        return HttpRequest.newBuilder()
            .uri(deployment.httpUriForContainer(containerName).resolve(url))
            .header("X-Request-ID", UUID.randomUUID().toString())
            .timeout(Duration.ofSeconds(30L))

    }

    HttpResponseVerificator sendRequest(HttpRequest httpRequest) {
        Optional<String> requestId = httpRequest.headers().firstValue('X-Request-ID')
        if( requestId.empty ) {
            log.info("Sending request ${httpRequest.method()} ${httpRequest.uri().toString()} with no X-Request-ID")
        }
        else {
            log.info("Sending request ${httpRequest.method()} ${httpRequest.uri().toString()} with X-Request-ID: ${requestId.get()}")
        }

        return new HttpResponseVerificator(httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()))
    }

    HttpResponseVerificator createAndSendGet(String containerName, String url) {
        HttpRequest.Builder requestBuilder = newRequestBuilder(containerName, url)
            .GET()

        return sendRequest(requestBuilder.build())
    }

    HttpResponseVerificator createAndSendPostWithBody(String containerName, String url, String contentType, HttpRequest.BodyPublisher bodyPublisher) {
        HttpRequest.Builder requestBuilder = newRequestBuilder(containerName, url)
            .POST(bodyPublisher)
            .header('Content-Type', contentType)

        return sendRequest(requestBuilder.build())
    }

    HttpResponseVerificator createAndSendPostWithJson(String containerName, String url, Object body) {
        return createAndSendPostWithBody(containerName,
            url,
            MediaType.JSON_UTF_8.toString(),
            HttpRequest.BodyPublishers.ofString(JsonMapper.encodeAsJson(body))
        )
    }

    HttpResponseVerificator createAndSendPatchWithBody(String containerName, String url, String contentType, HttpRequest.BodyPublisher bodyPublisher) {
        HttpRequest.Builder requestBuilder = newRequestBuilder(containerName, url)
            .method("PATCH", bodyPublisher)
            .header('Content-Type', contentType)

        return sendRequest(requestBuilder.build())
    }

    HttpResponseVerificator createAndSendPatchWithJson(String containerName, String url, Object body) {
        return createAndSendPatchWithBody(containerName,
            url,
            MediaType.JSON_UTF_8.toString(),
            HttpRequest.BodyPublishers.ofString(JsonMapper.encodeAsJson(body))
        )
    }
}
