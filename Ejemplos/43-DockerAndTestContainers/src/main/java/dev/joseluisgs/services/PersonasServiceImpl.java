package dev.joseluisgs.services;

import dev.joseluisgs.errors.PersonaError;
import dev.joseluisgs.errors.PersonaNotFound;
import dev.joseluisgs.model.Persona;
import dev.joseluisgs.repository.PersonasRepository;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PersonasServiceImpl implements PersonasService {
    private final PersonasRepository personasRepository;
    private final Logger logger = LoggerFactory.getLogger(PersonasServiceImpl.class);

    public PersonasServiceImpl(PersonasRepository personasRepository) {
        this.personasRepository = personasRepository;
    }

    @Override
    public Either<PersonaError, List<Persona>> getAll() {
        logger.info("Obteniendo personas...");
        return Either.right(personasRepository.getAll());
    }

    @Override
    public Either<PersonaError, Persona> getById(int id) {
        logger.info("Obteniendo persona por id...");
        try {
            var personaOptional = personasRepository.getById(id);
            if (personaOptional.isPresent()) {
                return Either.right(personaOptional.get());
            } else {
                return Either.left(new PersonaNotFound("Persona no encontrada con id: " + id));
            }
        } catch (Exception e) {
            return Either.left(new PersonaError("Error al obtener persona por id: " + id));
        }

    }

    @Override
    public Either<PersonaError, Persona> create(Persona persona) {
        logger.info("Creando persona...");
        try {
            return Either.right(personasRepository.create(persona));
        } catch (Exception e) {
            return Either.left(new PersonaError("Error al crear persona"));
        }
    }

    @Override
    public Either<PersonaError, Persona> update(long id, Persona persona) {
        logger.info("Actualizando persona...");
        // La busco
        try {
            if (personasRepository.getById(id).isPresent()) {
                return Either.right(personasRepository.update(id, persona));
            } else {
                return Either.left(new PersonaNotFound("Persona no encontrada con id: " + id));
            }
        } catch (Exception e) {
            return Either.left(new PersonaError("Error al actualizar persona con id: " + id));
        }
    }

    @Override
    public Either<PersonaError, Persona> delete(long id) {
        logger.info("Eliminando persona...");
        // La busco
        try {
            var personaOptional = personasRepository.getById(id);
            if (personaOptional.isPresent()) {
                personasRepository.delete(id);
                return Either.right(personaOptional.get());

            } else {
                return Either.left(new PersonaNotFound("Persona no encontrada con id: " + id));
            }
        } catch (Exception e) {
            return Either.left(new PersonaError("Error al eliminar persona con id: " + id));
        }
    }

}
