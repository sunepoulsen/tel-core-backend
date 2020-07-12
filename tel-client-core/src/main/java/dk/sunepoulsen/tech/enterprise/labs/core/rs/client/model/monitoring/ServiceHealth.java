package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.monitoring;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.BaseModel;

import java.util.Objects;

public class ServiceHealth implements BaseModel {
    private ServiceHealthStatusCode status;

    public ServiceHealth() {
    }

    public ServiceHealthStatusCode getStatus() {
        return status;
    }

    public void setStatus( ServiceHealthStatusCode status ) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceHealth that = (ServiceHealth) o;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        return "ServiceStatus{" +
            "status=" + status +
            '}';
    }
}
