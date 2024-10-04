package dev.joseluisgs.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class User {
    public static Long NEW_ID = 0L;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String job;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
