package dev.joseluisgs.personas.exceptions

sealed class PersonaException(message: String) : Exception(message) {
    class PersonaNotFoundException(id: String) : PersonaException("Persona no encontrada con ID: $id")
    class PersonaNotSavedException : PersonaException("Persona no se ha podido guardar")
    class PersonaNotUpdatedException(id: String) : PersonaException("Persona no se ha podido actualizar con ID: $id")
    class PersonaNotDeletedException(id: String) : PersonaException("Persona no se ha podido eliminar con ID: $id")
}