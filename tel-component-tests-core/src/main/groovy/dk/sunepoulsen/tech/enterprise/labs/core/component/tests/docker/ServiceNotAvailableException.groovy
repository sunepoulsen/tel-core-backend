package dk.sunepoulsen.tech.enterprise.labs.core.component.tests.docker

class ServiceNotAvailableException extends Exception {
    ServiceNotAvailableException(String message) {
        super(message)
    }
}
