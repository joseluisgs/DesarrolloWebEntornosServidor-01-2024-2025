package dev.joseluisgs.personas.services

import dev.joseluisgs.personas.model.Persona
import java.util.*

interface PersonasService {
    fun getAll(): List<Persona>
    fun findByName(name: String): List<Persona>
    fun getById(id: UUID): Persona
    fun save(persona: Persona): Persona
    fun update(id: UUID, persona: Persona): Persona
    fun delete(id: UUID): Persona
}