package dev.joseluisgs


import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

fun main() {
    val maxThreads = 1000
    val maxIterations = 100
    val lock = ReentrantLock(false)
    var contador = 0

    val threadPool = Executors.newFixedThreadPool(5)

    for (i in 1..maxThreads) {
        threadPool.execute {
            println("Hola soy Hilo $i")
            for (j in 1..maxIterations) {
                Thread.sleep(1)
                lock.withLock {
                    contador++
                    println("Hilo $i: $contador")
                }
                println("Hilo $i: $contador")
            }
            println("Hasta luego Hilo $i")
        }
    }

    threadPool.shutdown()
    while (!threadPool.isTerminated) {
        Thread.sleep(100)
    }

    println("Todos los hilos han finalizado")
    println("Contador final: $contador")
}