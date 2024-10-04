package dev.joseluisgs.personas.model

import java.time.LocalDateTime
import java.util.*

data class Persona(
    val ID: UUID = UUID.randomUUID(),
    val nombre: String,
    val edad: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)