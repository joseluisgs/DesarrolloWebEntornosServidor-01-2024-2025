package dev.joseluisgs

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

class RepositoryResult {
    private val personas = mutableListOf<Persona>()

    fun add(persona: Persona): Result<Persona, PersonaError> {
        // Validamos el nombre que no sea blanco, vacío o nulo
        if (persona.nombre.isBlank()) {
            return Err(PersonaError.NombreNoValido("El nombre no puede ser nulo o vacío"))
        }

        // Validamos la edad que sea mayor de 0
        if (persona.edad <= 0) {
            return Err(PersonaError.EdadNoValida("La edad debe ser mayor de 0"))
        }

        if (personas.contains(persona)) {
            return Err(PersonaError.PersonaNoValida("La persona ya existe"))
        }

        // Si el numero de segundos de la hora es par, lanzamos una excepción no controlada
        /*if (System.currentTimeMillis() % 2 == 0L) {
            throw RuntimeException("Error no controlado")
        }*/

        personas.add(persona)
        return Ok(persona)
    }

    fun present(persona: Persona): Result<Unit, PersonaError> {
        if (persona.nombre == "José Luis") {
            return Err(PersonaError.NoPuedesSerJoseLuis("No puedes ser José Luis"))
        }
        println(persona)
        return Ok(Unit)
    }
}