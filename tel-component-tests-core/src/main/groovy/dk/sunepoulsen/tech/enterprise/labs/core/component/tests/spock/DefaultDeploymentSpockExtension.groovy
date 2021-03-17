package dk.sunepoulsen.tech.enterprise.labs.core.component.tests.spock

import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.docker.DockerDeployment
import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.model.SpecInfo

abstract class DefaultDeploymentSpockExtension implements IGlobalExtension {
    static DockerDeployment deployment

    private String composeName
    private List<String> containerNames

    DefaultDeploymentSpockExtension(String composeName, List<String> containerNames) {
        this.composeName = composeName
        this.containerNames = containerNames
    }

    @Override
    void start() {
        deployment = new DockerDeployment(this.composeName, this.containerNames)
        deployment.deploy()
        deployment.waitForAvailable()
    }

    @Override
    void visitSpec(SpecInfo spec) {
    }

    @Override
    void stop() {
        deployment.undeploy()
    }
}
