package dk.sunepoulsen.tech.enterprise.labs.core.rs.client;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.exceptions.ClientException;
import io.reactivex.Single;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class AbstractIntegrator {
    protected final TechEnterpriseLabsClient httpClient;

    public AbstractIntegrator(TechEnterpriseLabsClient httpClient) {
        this.httpClient = httpClient;
    }

    protected <T> Single<T> mapClientExceptions(Throwable throwable) {
        if( throwable instanceof ClientException) {
            return Single.error(throwable);
        }

        if( throwable.getCause() != null ) {
            return mapClientExceptions(throwable.getCause());
        }

        return Single.error(throwable);
    }

    protected String createHttpQuery(Map<String, String> params) {
        if( params.isEmpty()) {
            return "";
        }

        StringBuffer result = new StringBuffer();

        result.append('?');
        params.forEach((key, value) -> {
            result.append(key);
            result.append('=');
            result.append(URLEncoder.encode(value, StandardCharsets.UTF_8));
            result.append('&');
        });
        result.deleteCharAt(result.length() - 1);

        return result.toString();
    }
}
