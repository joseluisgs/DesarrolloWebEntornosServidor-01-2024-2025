package dev.joseluisgs

import dev.joseluisgs.storage.TenistasStorageCsv
import org.lighthousegames.logging.logging
import java.io.File
import java.util.concurrent.CompletableFuture
import kotlin.system.measureTimeMillis

fun main() {
    val logger = logging()
    val storage = TenistasStorageCsv()

    logger.debug { "Cargando datos" }
    val time = measureTimeMillis {
        // paralelizamos la carga de los datos
        val listaFutura1 = CompletableFuture.supplyAsync { storage.import(File("data01.csv")) }
        val listaFutura2 = CompletableFuture.supplyAsync { storage.import(File("data02.csv")) }

        while (!listaFutura1.isDone || !listaFutura2.isDone) {
            logger.debug { "Esperando a que se carguen los datos" }
            Thread.sleep(20)
        }
        logger.debug { "Datos cargados" }
        val listaFinal = listaFutura1.get() + listaFutura2.get()
        // tenista con altura máxima y valor de la misma
        val tenistaMaxAltura = listaFinal.maxByOrNull { it.altura }
        println("Tenista con altura máxima: $tenistaMaxAltura y su altura es ${tenistaMaxAltura?.altura}")
    }

    println("Código ejecutado en: $time ms")
}