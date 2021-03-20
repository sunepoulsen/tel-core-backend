package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.exceptions;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.validation.ValidateViolationModel;
import lombok.Getter;

import java.util.Set;

@Getter
public class ModelValidateException extends RuntimeException {
    private final Set<ValidateViolationModel> violations;

    public ModelValidateException(String message, Set<ValidateViolationModel> violations) {
        super(message);
        this.violations = violations;
    }
}
