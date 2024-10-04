package dev.joseluisgs

sealed class PersonaException(message: String) : Exception(message) {
    class NombreNoValido(message: String) : PersonaException("El nombre no es válido: $message")
    class EdadNoValida(message: String) : PersonaException("La edad no es válida: $message")
    class PersonaNoValida(message: String) : PersonaException("La persona no es válida: $message")
    class NoPuedesSerJoseLuis(message: String) : PersonaException("No puedes ser José Luis: $message")
}