package dev.joseluisgs

class RepositoryException {
    private val personas = mutableListOf<Persona>()

    fun add(persona: Persona): Persona {
        // Validamos el nombre que no sea blanco, vacío o nulo
        if (persona.nombre.isBlank()) {
            throw PersonaException.NombreNoValido("El nombre no puede ser nulo o vacío")
        }

        // Validamos la edad que sea mayor de 0
        if (persona.edad <= 0) {
            throw PersonaException.EdadNoValida("La edad debe ser mayor de 0")
        }

        if (personas.contains(persona)) {
            throw PersonaException.PersonaNoValida("La persona ya existe")
        }

        // Si el numero de segundos de la hora es par, lanzamos una excepción no controlada
        /*if (System.currentTimeMillis() % 2 == 0L) {
            throw RuntimeException("Error no controlado")
        }*/

        personas.add(persona)
        return persona
    }

    fun present(persona: Persona) {
        if (persona.nombre == "José Luis") {
            throw PersonaException.NoPuedesSerJoseLuis("No puedes ser José Luis")
        }
        println(persona)
    }
}