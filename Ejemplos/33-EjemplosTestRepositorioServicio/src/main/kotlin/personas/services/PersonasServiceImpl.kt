package dev.joseluisgs.personas.services

import dev.joseluisgs.personas.exceptions.PersonaException
import dev.joseluisgs.personas.model.Persona
import dev.joseluisgs.personas.repository.PersonasRepository
import dev.joseluisgs.personas.services.cache.CachePersonas
import org.lighthousegames.logging.logging
import java.util.*

class PersonasServiceImpl(
    private val cache: CachePersonas,
    private val repository: PersonasRepository
) : PersonasService {
    private val logger = logging()
    override fun getAll(): List<Persona> {
        logger.debug { "Obteniendo del repositorio" }
        val personas = repository.getAll()
        logger.info { "Personas obtenidas: $personas" }
        return personas
    }

    override fun findByName(name: String): List<Persona> {
        logger.debug { "Buscando por nombre en el repositorio" }
        val personas = repository.findByName(name)
        logger.info { "Personas obtenidas: $personas" }
        return personas
    }

    override fun getById(id: UUID): Persona {
        // Primero buscamos en cache
        logger.debug { "Buscando por ID en cache" }
        val persona = cache.get(id)
        if (persona != null) {
            logger.info { "Persona encontrada en cache: $persona" }
            return persona
        }
        // Si no está en cache, buscamos en el repositorio
        logger.debug { "Buscando por ID en el repositorio" }
        val personaFromDb = repository.getById(id)
        if (personaFromDb != null) {
            logger.info { "Persona encontrada en el repositorio: $personaFromDb" }
            // Actualizamos el cache
            cache.put(personaFromDb.ID, personaFromDb)
            return personaFromDb
        }
        logger.error { "persona no encontrada con ID: $id" }
        throw PersonaException.PersonaNotFoundException(id.toString())
    }

    override fun save(persona: Persona): Persona {
        logger.debug { "Guardando en el repositorio persona $persona" }
        val result = repository.save(persona)
        logger.info { "Persona guardada en el repositorio: $result" }
        return result
    }

    override fun update(id: UUID, persona: Persona): Persona {
        logger.debug { "Actualizando en el repositorio con id: $id y datos: $persona" }
        // A la base de datos, eliminamos el persona antiguo del cache
        cache.remove(id)
        val result = repository.update(id, persona)
        if (result == null) {
            logger.error { "Persona no encontrada con ID: $id" }
            throw PersonaException.PersonaNotUpdatedException(id.toString())
        }
        logger.info { "Persona actualizada en el repositorio: $result" }
        return result
    }

    override fun delete(id: UUID): Persona {
        logger.debug { "Eliminando en el repositorio con id: $id" }
        // A la base de datos, eliminamos el persona antiguo del cache
        cache.remove(id)
        val result = repository.delete(id)
        if (result == null) {
            logger.error { "Persona no encontrada con ID: $id" }
            throw PersonaException.PersonaNotDeletedException(id.toString())
        }
        logger.info { "Persona eliminada en el repositorio: $result" }
        return result
    }
}