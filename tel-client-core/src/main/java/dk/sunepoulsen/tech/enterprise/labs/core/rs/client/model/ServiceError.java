package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model;

import java.util.Objects;

public class ServiceError implements BaseModel {
    private String code;
    private String param;
    private String message;

    public ServiceError() {
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getParam() {
        return param;
    }

    public void setParam( String param ) {
        this.param = param;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( !( o instanceof ServiceError) ) {
            return false;
        }
        ServiceError that = (ServiceError) o;
        return Objects.equals( code, that.code ) &&
            Objects.equals( param, that.param ) &&
            Objects.equals( message, that.message );
    }

    @Override
    public int hashCode() {
        return Objects.hash( code, param, message );
    }

    @Override
    public String toString() {
        return "ServiceError{" +
            "code='" + code + '\'' +
            ", param='" + param + '\'' +
            ", message='" + message + '\'' +
            '}';
    }


}
