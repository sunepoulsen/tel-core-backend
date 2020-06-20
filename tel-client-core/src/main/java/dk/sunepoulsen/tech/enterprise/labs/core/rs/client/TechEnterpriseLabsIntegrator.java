package dk.sunepoulsen.tech.enterprise.labs.core.rs.client;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.monitoring.ServiceStatus;
import io.reactivex.Single;

public class TechEnterpriseLabsIntegrator extends AbstractIntegrator {
    public TechEnterpriseLabsIntegrator(TechEnterpriseLabsClient httpClient) {
        super(httpClient);
    }

    public Single<ServiceStatus> status() {
        return Single.fromFuture(httpClient.get("/mx/status", ServiceStatus.class))
                .onErrorResumeNext(this::mapClientExceptions);
    }

}
