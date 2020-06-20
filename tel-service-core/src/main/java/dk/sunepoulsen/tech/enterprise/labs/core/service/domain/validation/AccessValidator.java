package dk.sunepoulsen.tech.enterprise.labs.core.service.domain.validation;

import dk.sunepoulsen.tech.enterprise.labs.core.service.domain.requests.ApiForbiddenException;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.context.TypeConstraintMappingContext;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Utility class to validate objects against a set a constraints.
 * <p>
 *     Hibernate and Spring have support of validating objects based on
 *     annotations of the properties of the object. Unfortunately they do
 *     not has support different validation constraints based on context.
 *     And we have a need to validate differently depending on the context
 *     when we validate bodies and variables in REST requests.
 * </p>
 * <p>
 *     For instance an operation may refer to any id if it is called with
 *     a system scope authorization, but only to a specific id when it is called
 *     with an user scoped authorization.
 * </p>
 * <p>
 *     To solve this problem we have come up with this class.
 * </p>
 */
public class AccessValidator {
    /**
     * Validates an object against a set of constraints.
     *
     * @param <T> Object type.
     * @param value The value to validate.
     * @param clazz Object class type. Required by hibernate.
     * @param mappings A Consumer functional interface that adds any constraints that
     *                 value should be validated against.
     *
     * @throws ApiForbiddenException Thrown if any constraints is violated.
     */
    public static <T> void validate( T value, Class<T> clazz, Consumer<TypeConstraintMappingContext<T>> mappings ) throws ApiForbiddenException {
        validate( null, value, clazz, mappings );
    }

    /**
     * Validates an object against a set of constraints.
     * <p>
     *     If we are validating primitive types a <code>paramName</code> needs to be passed be course the property
     *     name can not be found in <code>value</code>.
     * </p>
     *
     * @param paramName The name of the param to use in ApiForbiddenPlatformException. If <code>null</code>
     *                  is passed then the property path of the violation is used:
     *                  <code>violation.getPropertyPath().toString()</code>.
     * @param <T> Object type.
     * @param value The value to validate.
     * @param clazz Object class type. Required by hibernate.
     * @param mappings A Consumer functional interface that adds any constraints that
     *                 value should be validated against.
     *
     * @throws ApiForbiddenException Thrown if any constraints is violated.
     */
    public static <T> void validate( String paramName, T value, Class<T> clazz, Consumer<TypeConstraintMappingContext<T>> mappings ) throws ApiForbiddenException {
        HibernateValidatorConfiguration configuration = Validation
            .byProvider( HibernateValidator.class )
            .configure();

        ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        mappings.accept( constraintMapping.type( clazz ) );

        Validator validator = configuration.addMapping( constraintMapping )
            .buildValidatorFactory()
            .getValidator();

        Set<ConstraintViolation<T>> violations = validator.validate( value );
        if( !violations.isEmpty() ) {
            ConstraintViolation<T> violation = violations.iterator().next();

            String propertyName = paramName;
            if( propertyName == null ) {
                propertyName = violation.getPropertyPath().toString();
            }

            throw new ApiForbiddenException( propertyName, violation.getMessage() );
        }
    }

    /**
     * Validates an object against its constraint annotations.
     *
     * @param value The value to validate.
     *
     * @throws ApiForbiddenException Thrown if any constraints is violated.
     */
    public static <T> void validate( T value ) throws ApiForbiddenException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<T>> violations = validator.validate( value );
        if( !violations.isEmpty() ) {
            ConstraintViolation<T> violation = violations.iterator().next();
            throw new ApiForbiddenException( violation.getPropertyPath().toString(), violation.getMessage() );
        }
    }
}
