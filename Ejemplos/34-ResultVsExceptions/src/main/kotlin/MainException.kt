package dev.joseluisgs

fun main() {
    val repositoryException = RepositoryException()
    try {
        val p = repositoryException.add(Persona("José Luis", 29))
        //repositoryException.add(Persona("Pepe", 0))
        //repositoryException.add(Persona("", 39))
        // repositoryException.add(Persona("José Luis", -9))
        println("Todo OK")
        repositoryException.present(p)
    } catch (e: PersonaException.NombreNoValido) {
        println("Error: ${e.message}")
    } catch (e: PersonaException.EdadNoValida) {
        println("Error: ${e.message}")
    } catch (e: PersonaException.PersonaNoValida) {
        println("Error: ${e.message}")
    } catch (e: RuntimeException) {
        println("Error: ${e.message}")
    }


    try {
        val p = repositoryException.add(Persona("José Luis", -29))
        //repositoryException.add(Persona("Pepe", 0))
        //repositoryException.add(Persona("", 39))
        // repositoryException.add(Persona("José Luis", -9))
        println("Todo OK")
        repositoryException.present(p)
    } catch (e: Exception) {
        when (e) {
            is PersonaException.NombreNoValido -> println("Error: ${e.message}")
            is PersonaException.EdadNoValida -> println("Error: ${e.message}")
            is PersonaException.PersonaNoValida -> println("Error: ${e.message}")
            is RuntimeException -> println("Error: ${e.message}")
            else -> println("Error desconocido")
        }
    }

}