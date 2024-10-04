package dev.joseluisgs

import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.mapBoth

fun main() {
    val repositoryResult = RepositoryResult()
    repositoryResult.add(Persona("José Luis", -29))
        .andThen { repositoryResult.present(it) }
        .mapBoth(
            success = { println("Todo OK") },
            failure = {
                when (it) {
                    is PersonaError.NombreNoValido, is PersonaError.EdadNoValida -> {
                        println("Error en los datos de la persona: ${it.message}")
                    }

                    else -> {
                        println("otros errores: ${it.message}")
                    }
                }
            }
        )

}