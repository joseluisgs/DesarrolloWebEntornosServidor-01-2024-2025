package dev.joseluisgs

import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main() {
    println("Iniciando programa")
    val h1 = thread {
        println("Hola soy H1 ")
        Thread.sleep(2000)
        println("Hasta luego H1")
    }

    val h2 = thread {
        println("Hola soy H2 ")
        Thread.sleep(1000)
        println("Hasta luego H2")
    }
    h1.join()
    h2.join()

    println("Fin de programa")
    exitProcess(0)
}