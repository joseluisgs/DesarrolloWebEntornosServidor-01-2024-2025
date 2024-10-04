package dev.joseluisgs

fun main() {
    val repositoryEither = RepositoryEither()
    repositoryEither.add(Persona("José Luis", -29))
        .flatMap { repositoryEither.present(it) }
        .peek { println("Todo OK") }
        .peekLeft {
            when (it) {
                is PersonaError.NombreNoValido, is PersonaError.EdadNoValida -> {
                    println("Error en los datos de la persona: ${it.message}")
                }

                else -> {
                    println("Otros errores: ${it.message}")
                }
            }
        }
}
