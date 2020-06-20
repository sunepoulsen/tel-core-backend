package dk.sunepoulsen.tech.enterprise.labs.core.service.domain.interceptors;

import org.apache.log4j.MDC;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

class RequestLoggingInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger( RequestLoggingInterceptor.class );

    private static final String HEADER_REQUEST_ID_NAME = "X-Request-ID";
    private static final String ATTRIBUTE_REQUEST_IS_LOGGED_NAME = "RequestLoggingInterceptor.request.is.logged";
    private static final String ATTRIBUTE_STOP_WATCH_NAME = "RequestLoggingInterceptor.stop.watch";
    private static final String ATTRIBUTE_STOP_WATCH_TAG_NAME = "RequestLoggingInterceptor.stop.watch.tag";

    RequestLoggingInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler ) throws Exception {
        StopWatch watch = new Log4JStopWatch();

        String requestId = httpServletRequest.getHeader( HEADER_REQUEST_ID_NAME );
        if( requestId == null || requestId.isEmpty() || requestId.isBlank() ) {
            requestId = UUID.randomUUID().toString();
        }
        MDC.put( "request.id", requestId );

        httpServletRequest.setAttribute( ATTRIBUTE_STOP_WATCH_NAME, watch );

        String requestPath = getRequestPath( handler );
        if( requestPath.isEmpty() ) {
            requestPath = httpServletRequest.getRequestURI();
        }

        String tag = httpServletRequest.getMethod().toLowerCase();
        tag += requestPath.replace( "/", "." );
        httpServletRequest.setAttribute( ATTRIBUTE_STOP_WATCH_TAG_NAME, tag );

        log.info( "Starting processing of request: {} {} ==> {}", httpServletRequest.getMethod().toUpperCase(), requestPath, httpServletRequest.getRequestURI() );

        httpServletRequest.setAttribute( ATTRIBUTE_REQUEST_IS_LOGGED_NAME, Boolean.FALSE );
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView ) throws Exception {
        if( !Boolean.FALSE.equals(httpServletRequest.getAttribute(ATTRIBUTE_REQUEST_IS_LOGGED_NAME))) {
            return;
        }

        String requestPath = getRequestPath( handler );
        if( requestPath.isEmpty() ) {
            requestPath = httpServletRequest.getRequestURI();
        }
        log.info( "Response code: {}", httpServletResponse.getStatus() );
        log.info( "Done processing of request: {} {} ==> {}", httpServletRequest.getMethod().toUpperCase(), requestPath, httpServletRequest.getRequestURI() );

        Object watch = httpServletRequest.getAttribute( ATTRIBUTE_STOP_WATCH_NAME );
        if( watch instanceof StopWatch ) {
            StopWatch stopWatch = ((StopWatch)watch);
            stopWatch.stop( httpServletRequest.getAttribute( ATTRIBUTE_STOP_WATCH_TAG_NAME ).toString() + "." + httpServletResponse.getStatus() );
        }

        httpServletRequest.setAttribute( ATTRIBUTE_REQUEST_IS_LOGGED_NAME, Boolean.TRUE );
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex ) throws Exception {
        this.postHandle(httpServletRequest, httpServletResponse, handler, null);
    }

    private String getRequestPath( Object handler ) {
        if( !(handler instanceof HandlerMethod) ) {
            return "";
        }

        String classRequestPath = "";
        String methodRequestPath = "";

        HandlerMethod hm = (HandlerMethod)handler;

        if( hm.getMethod().isAnnotationPresent( RequestMapping.class ) ) {
            String[] mappingValue = hm.getMethod().getAnnotation( RequestMapping.class ).value();
            if( mappingValue != null && mappingValue.length > 0 ) {
                methodRequestPath = mappingValue[ 0 ];
            }
        }

        if( hm.getMethod().getDeclaringClass().isAnnotationPresent( RequestMapping.class ) ) {
            String[] mappingValue = hm.getMethod().getDeclaringClass().getAnnotation( RequestMapping.class ).value();
            if( mappingValue != null && mappingValue.length > 0 ) {
                classRequestPath = mappingValue[ 0 ];
            }
        }

        String requestPath = classRequestPath + methodRequestPath;
        if( !requestPath.startsWith( "/" ) ) {
            requestPath = "/" + requestPath;
        }

        return requestPath;
    }
}
