package dev.joseluisgs.services;

import dev.joseluisgs.errors.PersonaError;
import dev.joseluisgs.model.Persona;
import io.vavr.control.Either;

import java.util.List;

public interface PersonasService {
    Either<PersonaError, List<Persona>> getAll();

    Either<PersonaError, Persona> getById(int id);

    Either<PersonaError, Persona> create(Persona persona);

    Either<PersonaError, Persona> update(long id, Persona persona);

    Either<PersonaError, Persona> delete(long id);
}
