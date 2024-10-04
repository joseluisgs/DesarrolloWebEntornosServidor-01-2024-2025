package dev.joseluisgs

import dev.joseluisgs.models.Tenista
import dev.joseluisgs.storage.TenistasStorageCsvSuspend
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.lighthousegames.logging.logging
import java.io.File
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val logger = logging()
    val storage = TenistasStorageCsvSuspend()

    var lista1 = listOf<Tenista>() //esto es un poco burro porque estamos sobreescrbiendo la lista
    var lista2 = listOf<Tenista>()

    logger.debug { "Cargando datos" }
    val time = measureTimeMillis {
        // paralelizamos la carga de los datos
        val c1 = launch {
            lista1 = storage.import(File("data01.csv"))
        }
        val c2 = launch {
            lista2 = storage.import(File("data02.csv"))
        }

        c1.join()
        c2.join()

        val listaFinal = lista1 + lista2
        logger.debug { "Datos cargados" }
        // tenista con altura máxima y valor de la misma
        val tenistaMaxAltura = listaFinal.maxByOrNull { it.altura }
        println("Tenista con altura máxima: $tenistaMaxAltura y su altura es ${tenistaMaxAltura?.altura}")
    }

    println("Código ejecutado en: $time ms")
}