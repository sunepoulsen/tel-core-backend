package dk.sunepoulsen.tech.enterprise.labs.core.service.domain.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by sunepoulsen on 27/11/2016.
 */
@Configuration
public class InterceptorRegistration implements WebMvcConfigurer {
    private static final Logger log = LoggerFactory.getLogger( InterceptorRegistration.class );

    @Override
    public void addInterceptors( InterceptorRegistry registry ) {
        log.info( "Register interceptor: RequestLoggingInterceptor" );
        registry.addInterceptor( new RequestLoggingInterceptor() );
    }
}
