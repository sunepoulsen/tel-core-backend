package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.transformations;

import dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model.PaginationResult;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class PaginationTransformations {
    public static <T> PaginationResult<T> toPaginationResult(Page<T> page) {
        PaginationResult<T> paginationResult = new PaginationResult<>();
        paginationResult.getMetadata().setPage(page.getNumber());
        paginationResult.getMetadata().setSize(page.getSize());
        paginationResult.getMetadata().setTotalItems(page.getTotalElements());
        paginationResult.getMetadata().setTotalPages(page.getTotalPages());
        paginationResult.setResults(page.stream()
            .collect(Collectors.toUnmodifiableList())
        );

        return paginationResult;
    }
}
