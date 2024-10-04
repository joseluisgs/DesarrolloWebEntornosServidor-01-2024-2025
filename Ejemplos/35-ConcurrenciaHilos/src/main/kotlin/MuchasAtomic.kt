package dev.joseluisgs

import java.util.concurrent.atomic.AtomicInteger

fun main() {
    val maxThreads = 1000
    val maxIterations = 100
    val threads = mutableListOf<Thread>()
    val atomicContador = AtomicInteger(0)

    for (i in 1..maxThreads) {
        threads.add(Thread {
            println("Hola soy Hilo $i")
            for (j in 1..maxIterations) {
                Thread.sleep(1)
                atomicContador.incrementAndGet()
                println("Hilo $i: $atomicContador")
            }
            println("Hasta luego Hilo $i")

        })
    }

    threads.forEach { it.start() }
    threads.forEach { it.join() }
    println("Todos los hilos han finalizado")
    println("Contador final: $atomicContador")

}