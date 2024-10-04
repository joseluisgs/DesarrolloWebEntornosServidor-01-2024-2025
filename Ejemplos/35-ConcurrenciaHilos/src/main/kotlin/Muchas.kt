package dev.joseluisgs

fun main() {
    val maxThreads = 1000
    val maxIterations = 100
    val threads = mutableListOf<Thread>()
    var contador = 0

    for (i in 1..maxThreads) {
        threads.add(Thread {
            println("Hola soy Hilo $i")
            for (j in 1..maxIterations) {
                Thread.sleep(1)
                contador++
                println("Hilo $i: $contador")
            }
            println("Hasta luego Hilo $i")

        })
    }

    threads.forEach { it.start() }
    threads.forEach { it.join() }
    println("Todos los hilos han finalizado")
    println("Contador final: $contador")

}