package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.exceptions;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.ServiceError;

import java.net.http.HttpResponse;

public class ClientNotFoundException extends ClientResponseException {
    public ClientNotFoundException(HttpResponse<String> response, ServiceError serviceError) {
        super(response, serviceError);
    }

    public ClientNotFoundException(HttpResponse<String> response, ServiceError serviceError, Throwable throwable) {
        super(response, serviceError, throwable);
    }

    @Override
    public String toString() {
        return "ClientNotFoundException{} " + super.toString();
    }
}
