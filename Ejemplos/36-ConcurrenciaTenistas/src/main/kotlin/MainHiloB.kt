package dev.joseluisgs

import dev.joseluisgs.models.Tenista
import dev.joseluisgs.storage.TenistasStorageCsv
import org.lighthousegames.logging.logging
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun main() {
    val logger = logging()
    val storage = TenistasStorageCsv()
    lateinit var tenistaMaxAltura1: Tenista
    lateinit var tenistaMaxAltura2: Tenista

    logger.debug { "Cargando datos" }

    val time = measureTimeMillis {
        // paralelizamos la carga de los datos
        val th1 = thread {
            val lista1 = storage.import(java.io.File("data01.csv"))
            tenistaMaxAltura1 = lista1.maxByOrNull { it.altura }!!
        }
        val th2 = thread {
            val lista2 = storage.import(java.io.File("data02.csv"))
            tenistaMaxAltura2 = lista2.maxByOrNull { it.altura }!!
        }

        th1.join()
        th2.join()

        logger.debug { "Datos cargados" }
        // tenista con altura máxima y valor de la misma
        val tenistaMaxAltura =
            if (tenistaMaxAltura1.altura > tenistaMaxAltura2.altura) tenistaMaxAltura1 else tenistaMaxAltura2
        println("Tenista con altura máxima: $tenistaMaxAltura y su altura es ${tenistaMaxAltura.altura}")
    }

    println("Código ejecutado en: $time ms")
}