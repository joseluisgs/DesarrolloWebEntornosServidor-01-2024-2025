package dev.joseluisgs.repository;

import dev.joseluisgs.model.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonasRepository {
    List<Persona> getAll();

    Optional<Persona> getById(long id);

    Persona create(Persona persona);

    Persona update(long id, Persona persona);

    boolean delete(long id);
}
