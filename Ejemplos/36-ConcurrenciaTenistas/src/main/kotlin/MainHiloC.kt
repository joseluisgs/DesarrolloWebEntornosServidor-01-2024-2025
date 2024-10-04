package dev.joseluisgs

import dev.joseluisgs.models.Tenista
import dev.joseluisgs.storage.TenistasStorageCsv
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun main() {
    val storage = TenistasStorageCsv()
    var lista = mutableListOf<Tenista>()

    val time = measureTimeMillis {
        // paralelizamos la carga de los datos
        val th1 = thread {
            storage.import(java.io.File("data01.csv")).let { lista.addAll(it) }
        }
        val th2 = thread {
            storage.import(java.io.File("data02.csv")).let { lista.addAll(it) }
        }

        th1.join()
        th2.join()

        // tenista con altura máxima y valor de la misma
        val tenistaMaxAltura = lista.maxByOrNull { it.altura }
        println("Tenista con altura máxima: $tenistaMaxAltura y su altura es ${tenistaMaxAltura?.altura}")
    }

    println("Código ejecutado en: $time ms")
}