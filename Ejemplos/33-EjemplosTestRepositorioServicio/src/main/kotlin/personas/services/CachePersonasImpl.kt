package dev.joseluisgs.personas.services

import dev.joseluisgs.personas.model.Persona
import dev.joseluisgs.personas.services.cache.CachePersonas
import java.util.*

class CachePersonasImpl : CachePersonas {

    private val cache: MutableMap<UUID, Persona> = mutableMapOf()

    override fun get(key: UUID): Persona? = cache[key]

    override fun put(key: UUID, value: Persona) {
        cache[key] = value
    }

    override fun remove(key: UUID) {
        cache.remove(key)
    }

    override fun clear() = cache.clear()
    override fun size() = cache.size
}