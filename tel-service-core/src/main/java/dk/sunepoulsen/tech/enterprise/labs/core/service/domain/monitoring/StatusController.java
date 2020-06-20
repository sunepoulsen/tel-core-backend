package dk.sunepoulsen.tech.enterprise.labs.core.service.domain.monitoring;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.monitoring.ServiceMemoryStatus;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.monitoring.ServiceStatus;
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.monitoring.ServiceStatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static dk.sunepoulsen.tech.enterprise.labs.core.service.utils.SpringBootApplicationUtils.MONITORY_ENDPOINT_PATH;

@RestController
public class StatusController {
    public final String STATUS_PATH = MONITORY_ENDPOINT_PATH + "/status";

    @RequestMapping( value = STATUS_PATH, method = RequestMethod.GET )
    @ResponseStatus( HttpStatus.OK )
    public ServiceStatus status() {
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatus( ServiceStatusCode.OK );

        ServiceMemoryStatus memoryStatus = new ServiceMemoryStatus();
        memoryStatus.setMax( Runtime.getRuntime().maxMemory() );
        memoryStatus.setTotal( Runtime.getRuntime().totalMemory() );
        memoryStatus.setFree( Runtime.getRuntime().freeMemory() );
        memoryStatus.setUsed( memoryStatus.getTotal() - memoryStatus.getFree() );
        serviceStatus.setMemory( memoryStatus );

        return serviceStatus;
    }
}
