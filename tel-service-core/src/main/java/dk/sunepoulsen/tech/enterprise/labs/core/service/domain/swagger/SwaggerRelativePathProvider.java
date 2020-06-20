package dk.sunepoulsen.tech.enterprise.labs.core.service.domain.swagger;

import springfox.documentation.spring.web.paths.RelativePathProvider;

import javax.servlet.ServletContext;

public class SwaggerRelativePathProvider extends RelativePathProvider {
    private String applicationBasePath;

    public SwaggerRelativePathProvider(ServletContext servletContext, String applicationBasePath ) {
        super( servletContext );
        this.applicationBasePath = applicationBasePath;
    }

    @Override
    public String getApplicationBasePath() {
        return this.applicationBasePath;
    }
}
