package dk.sunepoulsen.tech.enterprise.labs.core.service.domain.swagger;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfiguration {
    private SwaggerProperties properties;
    private ServletContext servletContext;

    @Autowired
    SwaggerConfiguration( SwaggerProperties properties, ServletContext servletContext ) {
        this.properties = properties;
        this.servletContext = servletContext;
    }

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        Docket docket = new Docket( DocumentationType.SWAGGER_2 )
            .useDefaultResponseMessages( false );

        if( properties.getHost() != null ) {
            docket = docket.host( properties.getHost() );
        }

        if( properties.getBasePath() != null ) {
            docket = docket.pathProvider( new SwaggerRelativePathProvider(this.servletContext, properties.getBasePath()) );
        }

        docket = docket
            .apiInfo( apiInfo() )
            .select()
            .paths( Predicates.not( PathSelectors.regex( "/error.*" ) ) )
            .build();

        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title( properties.getTitle() )
            .description( properties.getDescription() )
            .contact( new Contact( properties.getContact().getName(), properties.getContact().getUrl(), properties.getContact().getEmail() ) )
            .license( properties.getLicense() )
            .licenseUrl( properties.getLicenseUrl() )
            .version( properties.getVersion() )
            .build();
    }
}
