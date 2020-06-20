package dk.sunepoulsen.tech.enterprise.labs.core.service.domain.requests;

import io.reactivex.Observable;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.function.Function;

public class DeferredResults {
    public static <T> DeferredResult<T> of(T value ) {
        return of( Observable.just( value ) );
    }

    public static <T> DeferredResult<T> of(Observable<T> observable ) {
        return of( observable, ApiInternalServerException::new );
    }

    public static <T> DeferredResult<T> of(Observable<T> observable, Function<Throwable, ApiException> errorMapper ) {
        DeferredResult<T> deferredResult = new DeferredResult<>();

        observable.subscribe( deferredResult::setResult, throwable -> {
            try {
                if( throwable instanceof ApiException) {
                    deferredResult.setErrorResult( throwable );
                    return;
                }

                if( throwable instanceof UnsupportedOperationException ) {
                    deferredResult.setErrorResult( throwable );
                    return;
                }

                ApiException apiThrowable = errorMapper.apply( throwable );
                if( apiThrowable != null ) {
                    deferredResult.setErrorResult( apiThrowable );
                    return;
                }

                deferredResult.setErrorResult( new ApiInternalServerException( throwable ) );
            }
            catch( Throwable ex ) {
                deferredResult.setErrorResult( new ApiInternalServerException( ex ) );
            }
        } );

        return deferredResult;
    }

    public static <T> boolean wait( DeferredResult<T> deferredResult ) throws InterruptedException {
        return wait( deferredResult, 30000L );
    }

    public static <T> boolean wait(DeferredResult<T> deferredResult, Long millis ) throws InterruptedException {
        return wait( deferredResult, millis, 100L );
    }

    public static <T> boolean wait(DeferredResult<T> deferredResult, Long millis, Long sleepTime ) throws InterruptedException {
        long start = System.currentTimeMillis();
        long end = start;

        while( (end - start < millis) && !deferredResult.isSetOrExpired() ) {
            Thread.sleep( sleepTime );
            end = System.currentTimeMillis();
        }

        return end - start < millis;
    }

}
