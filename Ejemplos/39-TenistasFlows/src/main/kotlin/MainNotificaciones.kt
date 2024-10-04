package dev.joseluisgs

import dev.joseluisgs.repository.TenistasRepository
import dev.joseluisgs.storage.TenistasStorageCsv
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

fun main(): Unit = runBlocking {
    val storage = TenistasStorageCsv()
    val repositorio = TenistasRepository()

    val job1 = launch {
        storage.import(File("data01.csv"))
            .onStart { println("Iniciando importación Data01") }
            .onCompletion { println("Finalizando importación Data01") }
            .collect { tenista -> repositorio.save(tenista) }
    }

    val job2 = launch {
        storage.import(File("data02.csv"))
            .onStart { println("Iniciando importación Data02") }
            .onCompletion { println("Finalizando importación Data02") }
            .collect { tenista -> repositorio.save(tenista) }
    }

    val job3 = launch {
        repositorio.notificaciones
            .distinctUntilChanged() // Solo notificaciones distintas, para no repetir
            .collect { notificacion -> println("🔥Notificación: $notificacion") }
    }

    val job4 = launch {
        repositorio.listaActualizada.collect { notificacion -> println("🪰Lista nueva: $notificacion") }
    }

    job1.join()
    job2.join()

    delay(1000)
    repositorio.delete(1)
    delay(1000)
    repositorio.delete(2)
    delay(1000)
    repositorio.update(repositorio.findById(3)!!.copy(puntos = 1000))
    delay(1000)
    
    job3.cancel()
    job4.cancel()
}



