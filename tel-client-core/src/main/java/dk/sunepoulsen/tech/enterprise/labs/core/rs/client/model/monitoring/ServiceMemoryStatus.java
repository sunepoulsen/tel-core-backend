package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.monitoring;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.BaseModel;

import java.util.Objects;

/**
 * Structure of a service memory status
 */
public class ServiceMemoryStatus implements BaseModel {
    /**
     * Max memory of the JVM in bytes
     */
    private Long max;

    /**
     * Total memory of the JVM in bytes
     */
    private Long total;

    /**
     * Free memory of the JVM in bytes
     */
    private Long free;

    /**
     * Used memory of the JVM in bytes
     */
    private Long used;

    public ServiceMemoryStatus() {
    }

    public Long getMax() {
        return max;
    }

    public void setMax( Long max ) {
        this.max = max;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal( Long total ) {
        this.total = total;
    }

    public Long getFree() {
        return free;
    }

    public void setFree( Long free ) {
        this.free = free;
    }

    public Long getUsed() {
        return used;
    }

    public void setUsed( Long used ) {
        this.used = used;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( !( o instanceof ServiceMemoryStatus) ) {
            return false;
        }
        ServiceMemoryStatus that = (ServiceMemoryStatus) o;
        return Objects.equals( max, that.max ) &&
            Objects.equals( total, that.total ) &&
            Objects.equals( free, that.free ) &&
            Objects.equals( used, that.used );
    }

    @Override
    public int hashCode() {
        return Objects.hash( max, total, free, used );
    }

    @Override
    public String toString() {
        return "ServiceMemoryStatus{" +
            "max=" + max +
            ", total=" + total +
            ", free=" + free +
            ", used=" + used +
            '}';
    }
}
