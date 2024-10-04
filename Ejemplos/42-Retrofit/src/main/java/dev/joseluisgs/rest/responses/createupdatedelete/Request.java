package dev.joseluisgs.rest.responses.createupdatedelete;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Request {

    @JsonProperty("name")
    private String name;

    @JsonProperty("job")
    private String job;

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}