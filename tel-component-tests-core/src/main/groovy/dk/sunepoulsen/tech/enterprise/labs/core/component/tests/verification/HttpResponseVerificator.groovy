package dk.sunepoulsen.tech.enterprise.labs.core.component.tests.verification

import groovy.json.JsonSlurper

import java.net.http.HttpResponse

class HttpResponseVerificator {
    private HttpResponse<String> httpResponse

    HttpResponseVerificator(HttpResponse<String> httpResponse) {
        this.httpResponse = httpResponse
    }

    void responseCode(int expected) {
        assert httpResponse.statusCode() == expected
    }

    void contentType(String expected) {
        Optional<String> contentType = httpResponse.headers().firstValue('Content-Type')
        assert contentType.isPresent()
        assert contentType.get() == expected
    }

    void contentTypeIsJson() {
        contentType('application/json')
    }

    void bodyIsJson() {
        assert bodyAsJson() != null
    }

    def bodyAsJson() {
        return new JsonSlurper().parse(httpResponse.body().getBytes('UTF-8'))
    }
}
