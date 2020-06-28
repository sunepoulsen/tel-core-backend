package dk.sunepoulsen.tech.enterprise.labs.core.component.tests.http

import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.docker.DockerDeployment
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.verification.HttpResponseVerificator

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

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
        return new HttpResponseVerificator(httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()))
    }

}
