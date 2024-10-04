package dev.joseluisgs

import io.vavr.control.Either

class RepositoryEither {
    private val personas = mutableListOf<Persona>()

    fun add(persona: Persona): Either<PersonaError, Persona> {
        // Validamos el nombre que no sea blanco, vacío o nulo
        if (persona.nombre.isBlank()) {
            return Either.left(PersonaError.NombreNoValido("El nombre no puede ser nulo o vacío"))
        }

        // Validamos la edad que sea mayor de 0
        if (persona.edad <= 0) {
            return Either.left(PersonaError.EdadNoValida("La edad debe ser mayor de 0"))
        }

        if (personas.contains(persona)) {
            return Either.left(PersonaError.PersonaNoValida("La persona ya existe"))
        }

        // Si el numero de segundos de la hora es par, lanzamos una excepción no controlada
        /*if (System.currentTimeMillis() % 2 == 0L) {
            throw RuntimeException("Error no controlado")
        }*/

        personas.add(persona)
        return Either.right(persona)
    }

    fun present(persona: Persona): Either<PersonaError, Unit> {
        if (persona.nombre == "José Luis") {
            return Either.left(PersonaError.NoPuedesSerJoseLuis("No puedes ser José Luis"))
        }
        println(persona)
        return Either.right(Unit)
    }
}