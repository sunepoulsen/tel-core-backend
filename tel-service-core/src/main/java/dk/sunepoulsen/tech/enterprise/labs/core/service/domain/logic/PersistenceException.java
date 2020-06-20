package dk.sunepoulsen.tech.enterprise.labs.core.service.domain.logic;

/**
 * Exception that is intended to report persistence errors
 * in services.
 */
public class PersistenceException extends RuntimeException {
    public PersistenceException( String message ) {
        super( message );
    }

    public PersistenceException( String message, Throwable cause ) {
        super( message, cause );
    }

    public PersistenceException( Exception ex ) {
        this( ex.getMessage(), ex );
    }
}
