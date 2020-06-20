package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.exceptions;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.ServiceError;

import java.net.http.HttpResponse;

public class ClientNotImplementedException extends ClientResponseException {
    public ClientNotImplementedException(HttpResponse<String> response, ServiceError serviceError) {
        super(response, serviceError);
    }

    public ClientNotImplementedException(HttpResponse<String> response, ServiceError serviceError, Throwable throwable) {
        super(response, serviceError, throwable);
    }

    @Override
    public String toString() {
        return "ClientNotImplementedException{} " + super.toString();
    }
}
