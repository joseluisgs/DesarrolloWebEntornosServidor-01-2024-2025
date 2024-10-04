package dev.joseluisgs.personas.repository

import dev.joseluisgs.personas.model.Persona
import dev.joseluisgs.personas.repository.common.Repository
import java.util.*

interface PersonasRepository : Repository<UUID, Persona> {
    fun findByName(name: String): List<Persona>
}