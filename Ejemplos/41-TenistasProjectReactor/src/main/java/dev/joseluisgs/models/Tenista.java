package dev.joseluisgs.models;

import java.time.LocalDate;

public record Tenista(
        Long id,
        String nombre,
        String pais,
        int altura,
        int peso,
        int puntos,
        Mano mano,
        LocalDate fechaNacimiento
) {
    public static Long NEW_ID = 0L;
}
