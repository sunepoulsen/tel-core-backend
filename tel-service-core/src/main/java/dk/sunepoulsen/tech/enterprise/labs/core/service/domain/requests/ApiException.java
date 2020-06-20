package dk.sunepoulsen.tech.enterprise.labs.core.service.domain.requests;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.ServiceError;

import java.util.Objects;

/**
 * Created by sunepoulsen on 27/11/2016.
 */
public class ApiException extends RuntimeException {
    private ServiceError serviceError;

    public ApiException( String message ) {
        this( null, null, message, null );
    }

    public ApiException( String message, Throwable ex ) {
        this( null, null, message, ex );
    }

    public ApiException( String param, String message ) {
        this( null, param, message, null );
    }

    public ApiException( String param, String message, Throwable ex ) {
        this( null, param, message, ex );
    }

    public ApiException( String code, String param, String message ) {
        this( code, param, message, null );
    }

    public ApiException( String code, String param, String message, Throwable ex ) {
        super( message, ex );

        serviceError = new ServiceError();
        serviceError.setCode( code );
        serviceError.setParam( param );
        serviceError.setMessage( message );
    }

    public ServiceError getServiceError() {
        return serviceError;
    }

    public void setServiceError( ServiceError serviceError ) {
        this.serviceError = serviceError;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( !( o instanceof ApiException) ) {
            return false;
        }
        ApiException that = (ApiException) o;
        return Objects.equals( serviceError, that.serviceError );
    }

    @Override
    public int hashCode() {
        return Objects.hash( serviceError );
    }

    @Override
    public String toString() {
        return "ApiException{" +
            "serviceError=" + serviceError +
            '}';
    }
}
