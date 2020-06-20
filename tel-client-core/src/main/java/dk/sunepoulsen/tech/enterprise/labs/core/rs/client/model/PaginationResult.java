package dk.sunepoulsen.tech.enterprise.labs.core.rs.client.model;

import java.util.List;
import java.util.Objects;

/**
 * Object with a list of results
 *
 * @param <T> Type of each item in the list of results.
 */
public class PaginationResult<T> implements BaseModel {
    private int page;
    private int totalPages;
    private long totalItems;

    /**
     * List of results
     */
    private List<T> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults( List<T> results ) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaginationResult<?> that = (PaginationResult<?>) o;
        return page == that.page &&
            totalPages == that.totalPages &&
            totalItems == that.totalItems &&
            Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, totalPages, totalItems, results);
    }

    @Override
    public String toString() {
        return "PaginationResult{" +
            "page=" + page +
            ", totalPages=" + totalPages +
            ", totalItems=" + totalItems +
            ", results=" + results +
            '}';
    }
}
