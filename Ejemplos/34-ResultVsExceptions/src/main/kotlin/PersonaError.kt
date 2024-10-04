package dev.joseluisgs

sealed class PersonaError(val message: String) {
    class NombreNoValido(message: String) : PersonaError("El nombre no es válido: $message")
    class EdadNoValida(message: String) : PersonaError("La edad no es válida: $message")
    class PersonaNoValida(message: String) : PersonaError("La persona no es válida: $message")
    class NoPuedesSerJoseLuis(message: String) : PersonaError("No puedes ser José Luis: $message")
}