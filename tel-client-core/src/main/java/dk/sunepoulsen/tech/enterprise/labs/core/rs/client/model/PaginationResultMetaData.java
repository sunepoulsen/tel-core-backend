package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model;

import lombok.Data;

@Data
public class PaginationResultMetaData implements BaseModel {
    private int page;
    private int totalPages;
    private long totalItems;
    private int size;
}
