package dev.joseluisgs.personas.services.cache

import dev.joseluisgs.personas.model.Persona
import java.util.*

interface CachePersonas : Cache<UUID, Persona>