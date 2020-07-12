package dk.sunepoulsen.tech.enterprise.labs.core.rs.client;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.monitoring.ServiceHealth;
import io.reactivex.Single;

public class TechEnterpriseLabsIntegrator extends AbstractIntegrator {
    public TechEnterpriseLabsIntegrator(TechEnterpriseLabsClient httpClient) {
        super(httpClient);
    }

    public Single<ServiceHealth> health() {
        return Single.fromFuture(httpClient.get("/actuator/health", ServiceHealth.class))
                .onErrorResumeNext(this::mapClientExceptions);
    }

}
