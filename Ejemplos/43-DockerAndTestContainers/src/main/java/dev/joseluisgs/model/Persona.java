package dev.joseluisgs.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Persona {
    private long id;
    private UUID uuid;
    private String nombre;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
