package dk.sunepoulsen.tech.enterprise.labs.core.component.tests.specification

import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.docker.DockerDeployment
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.http.HttpHelper
import spock.lang.Specification

class DeploymentSpecification extends Specification {

    protected static DockerDeployment deployment
    protected HttpHelper httpHelper

    void cleanupSpec() {
        deployment.undeploy()
    }

    protected static initDeployment(String composeName, List<String> telServices) {
        deployment = new DockerDeployment(composeName, telServices)
        deployment.deploy()
        deployment.waitForAvailable()
    }

    void setup() {
        this.httpHelper = new HttpHelper(deployment)
    }
}
