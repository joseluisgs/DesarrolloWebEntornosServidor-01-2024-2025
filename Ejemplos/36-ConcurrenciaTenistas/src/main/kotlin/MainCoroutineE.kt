package dev.joseluisgs

import dev.joseluisgs.storage.TenistasStorageCsvSuspend
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.lighthousegames.logging.logging
import java.io.File
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val logger = logging()
    val storage = TenistasStorageCsvSuspend()

    logger.debug { "Cargando datos" }
    val time = measureTimeMillis {
        // paralelizamos la carga de los datos
        val listaFutura1 = async { storage.import(File("data01.csv")) }
        val listaFutura2 = async { storage.import(File("data02.csv")) }

        while (!listaFutura1.isCompleted || !listaFutura2.isCompleted) {
            logger.debug { "Esperando a que se carguen los datos" }
            delay(20)
        }
        logger.debug { "Datos cargados" }
        val listaFinal = listaFutura1.await() + listaFutura2.await()
        // tenista con altura máxima y valor de la misma
        val tenistaMaxAltura = listaFinal.maxByOrNull { it.altura }
        println("Tenista con altura máxima: $tenistaMaxAltura y su altura es ${tenistaMaxAltura?.altura}")
    }

    println("Código ejecutado en: $time ms")
}