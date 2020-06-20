package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonNaming( PropertyNamingStrategy.SnakeCaseStrategy.class )
@JsonIgnoreProperties( ignoreUnknown = true )
public interface BaseModel {
}
