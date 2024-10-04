package dev.joseluisgs.rest.responses.getall;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGetAll {

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("total")
    private int total;

    @JsonProperty("data")
    private List<UserGetAll> data;

    @JsonProperty("page")
    private int page;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("support")
    private Support support;

    public int getPerPage() {
        return perPage;
    }

    public int getTotal() {
        return total;
    }

    public List<UserGetAll> getData() {
        return data;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public Support getSupport() {
        return support;
    }
}