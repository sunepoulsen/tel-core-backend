package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.validation;

import lombok.Data;

@Data
public class ValidateViolationModel {
    private String param;
    private String messageTemplate;
    private String message;
    private String invalidValue;
}
