package dev.joseluisgs.personas.repository

import dev.joseluisgs.personas.model.Persona
import org.lighthousegames.logging.logging
import java.time.LocalDateTime
import java.util.*


class PersonasRepositoryImpl : PersonasRepository {
    private val logger = logging()

    private val db = hashMapOf<UUID, Persona>()

    override fun findByName(name: String): List<Persona> {
        logger.debug { "Buscando por nombre: $name" }
        logger.info { "Total de coincidencias: ${db.values.count { it.nombre.contains(name, ignoreCase = true) }}" }
        return db.values.filter { it.nombre.contains(name, ignoreCase = true) }
    }

    override fun getAll(): List<Persona> {
        logger.debug { "Obteniendo todas las personas" }

        logger.info { "Total de personas: ${db.size}" }
        return db.values.toList()
    }

    override fun getById(id: UUID): Persona? {
        logger.debug { "Obteniendo persona por ID: $id" }

        val persona = db[id]
        if (persona == null) {
            logger.warn { "Persona con ID: $id no encontrada" }
        } else {
            logger.info { "Persona encontrada: $persona" }
        }
        return persona
    }

    override fun save(t: Persona): Persona {
        logger.debug { "Guardando persona: $t" }
        //val newId = UUID.randomUUID()
        val timeStamp = LocalDateTime.now()
        val newPersona = t.copy(createdAt = timeStamp, updatedAt = timeStamp)
        db[t.ID] = newPersona
        logger.info { "Persona guardada: $newPersona" }
        return newPersona
    }

    override fun update(id: UUID, t: Persona): Persona? {
        logger.debug { "Actualizando persona con ID: $id" }
        val persona = db[id]
        if (persona == null) {
            logger.warn { "Persona con ID: $id no encontrada" }
            return null
        } else {
            val updatedPersona = persona.copy(
                nombre = t.nombre,
                edad = t.edad,
                updatedAt = LocalDateTime.now()
            )
            db[id] = updatedPersona
            logger.info { "Persona actualizada: $updatedPersona" }
            return updatedPersona
        }
    }

    override fun delete(id: UUID): Persona? {
        logger.debug { "Borrando persona con ID: $id" }
        val persona = db[id]
        if (persona == null) {
            logger.warn { "Persona con ID: $id no encontrada" }
            return null
        } else {
            db.remove(id)
            logger.info { "Persona borrada: $persona" }
            return persona
        }
    }
}