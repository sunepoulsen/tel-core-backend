package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.exceptions;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.ServiceError;

import java.net.http.HttpResponse;

public class ClientBadRequestException extends ClientResponseException {
    public ClientBadRequestException(HttpResponse<String> response, ServiceError serviceError) {
        super(response, serviceError);
    }

    public ClientBadRequestException(HttpResponse<String> response, ServiceError serviceError, Throwable throwable) {
        super(response, serviceError, throwable);
    }

    @Override
    public String toString() {
        return "ClientNotImplementedException{} " + super.toString();
    }
}
