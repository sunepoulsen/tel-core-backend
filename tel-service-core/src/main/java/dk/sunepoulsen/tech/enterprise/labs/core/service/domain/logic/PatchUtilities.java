package dk.sunepoulsen.tech.enterprise.labs.core.service.domain.logic;

/**
 * Utility functions that is useful for patch operations.
 */
public class PatchUtilities {
    public static <T> T patchValue( T originalValue, T updateValue ) {
        if( updateValue != null ) {
            return updateValue;
        }

        return originalValue;
    }
}
