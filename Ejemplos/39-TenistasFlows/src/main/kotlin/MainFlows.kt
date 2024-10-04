package dev.joseluisgs

import dev.joseluisgs.models.Tenista
import dev.joseluisgs.storage.TenistasStorageCsv
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File


fun main() = runBlocking {
    val storage = TenistasStorageCsv()

    val job1 = launch {
        storage.import(File("data01.csv"))
            .onStart { println("Iniciando importación Data01") }
            .onCompletion { println("Finalizando importación Data01") }
            .onEach { tenista -> println("Tenista: $tenista") }
            .filter { tenista -> tenista.mano == Tenista.Mano.DIESTRO }
            .take(3)
            .collect { tenista -> println("Insertando Diestros: $tenista") }
    }

    val job2 = launch {
        storage.import(File("data02.csv"))
            .onStart { println("Iniciando importación Data02") }
            .onCompletion { println("Finalizando importación Data02") }
            .onEach { tenista -> println("Tenista: $tenista") }
            .filter { tenista -> tenista.mano == Tenista.Mano.ZURDO }
            .take(1)
            .collect { tenista -> println("Insertando Zurdos: $tenista") }
    }

    job1.join()
    job2.join()
}




