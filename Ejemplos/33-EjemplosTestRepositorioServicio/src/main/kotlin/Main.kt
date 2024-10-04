package dev.joseluisgs

import dev.joseluisgs.personas.repository.PersonasRepositoryImpl
import dev.joseluisgs.personas.services.CachePersonasImpl
import dev.joseluisgs.personas.services.PersonasServiceImpl

fun main() {
    val service = PersonasServiceImpl(
        CachePersonasImpl(),
        PersonasRepositoryImpl()
    )
}