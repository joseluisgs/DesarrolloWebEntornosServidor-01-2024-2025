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
            .orTimeout(300, java.util.concurrent.TimeUnit.MILLISECONDS)
        val listaFutura2 = CompletableFuture.supplyAsync { storage.import(File("data02.csv")) }

        try {
            logger.debug { "Datos cargados" }
            val listaFinal = listaFutura1.get() + listaFutura2.get()
            // tenista con altura máxima y valor de la misma
            val tenistaMaxAltura = listaFinal.maxByOrNull { it.altura }
            println("Tenista con altura máxima: $tenistaMaxAltura y su altura es ${tenistaMaxAltura?.altura}")
        } catch (e: Exception) {
            logger.error { "Error cargando datos, en CSV1, tiempo máximo excedido: ${e.message}" }
        }


    }

    println("Código ejecutado en: $time ms")
}