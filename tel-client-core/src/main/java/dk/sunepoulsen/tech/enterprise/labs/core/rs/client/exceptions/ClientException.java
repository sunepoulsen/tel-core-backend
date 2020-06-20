package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.exceptions;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.ServiceError;

import java.util.Objects;

public class ClientException extends RuntimeException {
    private ServiceError serviceError;

    public ClientException(ServiceError serviceError) {
        this(serviceError, null);
    }

    public ClientException(ServiceError serviceError, Throwable throwable) {
        super(serviceError.getMessage(), throwable);
        this.serviceError = serviceError;
    }

    public ServiceError getServiceError() {
        return serviceError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientException that = (ClientException) o;
        return Objects.equals(serviceError, that.serviceError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceError);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "serviceError=" + serviceError +
                '}';
    }
}
