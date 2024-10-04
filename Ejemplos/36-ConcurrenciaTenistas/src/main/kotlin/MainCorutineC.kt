package dev.joseluisgs

import dev.joseluisgs.storage.TenistasStorageCsv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.lighthousegames.logging.logging
import java.io.File
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val logger = logging()
    val storage = TenistasStorageCsv()

    logger.debug { "Cargando datos" }
    val time = measureTimeMillis {

        val lista1 = async { storage.import(File("data01.csv")) }
        val lista2 = async(Dispatchers.IO) { storage.import(File("data02.csv")) }

        val listaFinal = lista1.await() + lista2.await()
        logger.debug { "Datos cargados" }
        // tenista con altura máxima y valor de la misma
        val tenistaMaxAltura = listaFinal.maxByOrNull { it.altura }
        println("Tenista con altura máxima: $tenistaMaxAltura y su altura es ${tenistaMaxAltura?.altura}")
    }

    println("Código ejecutado en: $time ms")
}