package dev.joseluisgs.rest.responses.getall;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGetAll {

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("id")
    private int id;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("email")
    private String email;

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }
}