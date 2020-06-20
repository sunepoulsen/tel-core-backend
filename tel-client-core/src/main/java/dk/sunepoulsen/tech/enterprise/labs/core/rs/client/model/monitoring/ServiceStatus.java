package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.monitoring;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.BaseModel;

import java.util.Objects;

public class ServiceStatus implements BaseModel {
    private ServiceStatusCode status;
    private ServiceMemoryStatus memory;

    public ServiceStatus() {
    }

    public ServiceStatusCode getStatus() {
        return status;
    }

    public void setStatus( ServiceStatusCode status ) {
        this.status = status;
    }

    public ServiceMemoryStatus getMemory() {
        return memory;
    }

    public void setMemory( ServiceMemoryStatus memory ) {
        this.memory = memory;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( !( o instanceof ServiceStatus) ) {
            return false;
        }
        ServiceStatus that = (ServiceStatus) o;
        return status == that.status &&
            Objects.equals( memory, that.memory );
    }

    @Override
    public int hashCode() {
        return Objects.hash( status, memory );
    }

    @Override
    public String toString() {
        return "ServiceStatus{" +
            "status=" + status +
            ", memory=" + memory +
            '}';
    }
}
