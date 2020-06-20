package dk.sunepoulsen.tech.enterprise.labs.core.service.domain.requests;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sunepoulsen on 26/11/2016.
 */
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger log = LoggerFactory.getLogger( ServiceExceptionHandler.class );

    public ServiceExceptionHandler() {
    }

    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( ApiBadRequestException.class )
    @ResponseBody
    public ServiceError handleBadRequest(HttpServletRequest request, Exception ex ) {
        return handleCheckedException( ex );
    }

    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    @ExceptionHandler( ApiUnauthorizedException.class )
    @ResponseBody
    public ServiceError handleUnauthorized(HttpServletRequest request, Exception ex ) {
        return handleCheckedException( ex );
    }

    @ResponseStatus( HttpStatus.FORBIDDEN )
    @ExceptionHandler( ApiForbiddenException.class )
    @ResponseBody
    public ServiceError handleForbidden(HttpServletRequest request, Exception ex ) {
        return handleCheckedException( ex );
    }

    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ExceptionHandler( ApiNotFoundException.class )
    @ResponseBody
    public ServiceError handleNotFound(HttpServletRequest request, Exception ex ) {
        return handleCheckedException( ex );
    }

    @ResponseStatus( HttpStatus.CONFLICT )
    @ExceptionHandler( ApiConflictException.class )
    @ResponseBody
    public ServiceError handleConflict(HttpServletRequest request, Exception ex ) {
        return handleCheckedException( ex );
    }

    @ResponseStatus( HttpStatus.NOT_IMPLEMENTED )
    @ExceptionHandler( UnsupportedOperationException.class )
    @ResponseBody
    public ServiceError handleUnsupportedOperation(HttpServletRequest request, Exception ex ) {
        return handleCheckedException( ex );
    }

    @ResponseStatus( HttpStatus.BAD_GATEWAY )
    @ExceptionHandler( ApiBadGatewayException.class )
    @ResponseBody
    public ServiceError handleBadGateway(HttpServletRequest request, Exception ex ) {
        return handleCheckedException( ex );
    }

    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    @ExceptionHandler( ApiInternalServerException.class )
    @ResponseBody
    public ServiceError handleInternalServerError(HttpServletRequest request, Exception ex ) {
        return handleCheckedException( ex );
    }

    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    @ExceptionHandler( RuntimeException.class )
    @ResponseBody
    public ServiceError handleRuntimeException(HttpServletRequest request, Exception ex ) {
        ServiceError body = null;
        try {
            body = new ServiceError();
            body.setMessage( "Unable to process request" );

            return body;
        }
        catch( Exception e ) {
            log.info( e.getMessage(), e );
            return null;
        }
        finally {
            log.info( ex.getMessage(), ex );
            logResponseBody( body );
        }
    }

    private ServiceError handleCheckedException( Exception ex ) {
        ServiceError body = null;
        try {
            return body = extractErrorBody( ex );
        }
        catch( Exception e ) {
            log.info( e.getMessage(), e );
            return null;
        }
        finally {
            log.info( ex.getMessage() );
            log.debug( "Exception", ex );
            logResponseBody( body );
        }
    }

    private ServiceError extractErrorBody( Exception ex ) {
        if( ex instanceof ApiException) {
            return ((ApiException)ex).getServiceError();
        }

        ServiceError serviceError = new ServiceError();
        serviceError.setMessage(ex.getMessage());

        return serviceError;
    }

    private static void logResponseBody( ServiceError body ) {
        log.info( "Returned body: {}", body.toString() );
    }
}
