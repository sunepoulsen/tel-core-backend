package dk.sunepoulsen.tech.enterprise.labs.core.component.tests.docker

import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.util.ProcessUtils
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.TechEnterpriseLabsClient
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.TechEnterpriseLabsIntegrator
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.monitoring.ServiceStatus
import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.monitoring.ServiceStatusCode
import groovy.util.logging.Slf4j

@Slf4j
class DockerDeployment {

    static final Long DEFAULT_TIMEOUT = 60000L
    static final Long DEFAULT_SLEEP = 500L

    private String composeName
    private List<String> telServices
    private File composeDirectory

    DockerDeployment(String composeName, List<String> telServices) {
        this.composeName = composeName
        this.telServices = telServices
        this.composeDirectory = new File("docker/${composeName}")
    }

    void deploy() {
        log.info( 'Starting deployment in {}', composeDirectory )

        ProcessUtils.execute('docker-compose up -d', composeDirectory)
    }

    void undeploy() {
        log.info( 'Stopping deployment in {}', composeDirectory )

        ProcessUtils.execute('docker-compose stop', composeDirectory)
        ProcessUtils.execute('docker-compose rm -f', composeDirectory)
    }

    TechEnterpriseLabsClient createClient(String containerName) {
        String port = DockerIntegrator.findExternalPort("${composeName}_${containerName}_1", DockerIntegrator.PRIVATE_SERVICE_PORT)
        return new TechEnterpriseLabsClient(URI.create("http://localhost:${port}"))
    }

    URI httpUriForContainer(String containerName) {
        String port = DockerIntegrator.findExternalPort("${composeName}_${containerName}_1", DockerIntegrator.PRIVATE_SERVICE_PORT)
        return new URI("http://localhost:${port}")
    }

    boolean waitForAvailable() {
        this.telServices.each { containerName ->
            if( !waitForAvailable(containerName) ) {
                return false
            }
        }

        return true
    }

    boolean waitForAvailable(String containerName) {
        TechEnterpriseLabsIntegrator integrator = new TechEnterpriseLabsIntegrator(createClient(containerName))
        return waitForService(integrator)
    }

    static boolean waitForService( TechEnterpriseLabsIntegrator integrator ) {
        long start = System.currentTimeMillis()

        while( ( System.currentTimeMillis() - start ) < DEFAULT_TIMEOUT ) {
            try {
                log.debug( "Waiting for service to be available" )
                if( isAvailable(integrator) ) {
                    long spent = System.currentTimeMillis() - start
                    log.debug( "Service available after {}ms", spent )

                    return true
                }
            }
            catch( Exception ex ) {
                log.debug( "Exception from service: {}", ex.getMessage() )
            }

            try {
                Thread.sleep( DEFAULT_SLEEP )
            }
            catch( InterruptedException ex ) {
                log.debug( "Unable to sleep thread: " + ex.getMessage(), ex )
            }
        }

        return false
    }

    static boolean isAvailable(TechEnterpriseLabsIntegrator integrator) {
        ServiceStatus serviceStatus = integrator.status().blockingGet()
        if( serviceStatus != null ) {
            return serviceStatus.getStatus().equals( ServiceStatusCode.OK )
        }

        return false
    }

}
