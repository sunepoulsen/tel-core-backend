package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * Object with a list of results
 *
 * @param <T> Type of each item in the list of results.
 */
@Data
public class PaginationResult<T> implements BaseModel {
    private PaginationResultMetaData metadata;

    /**
     * List of results
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@modelClass")
    private List<T> results;

    public PaginationResult() {
        this.metadata = new PaginationResultMetaData();
    }
}
