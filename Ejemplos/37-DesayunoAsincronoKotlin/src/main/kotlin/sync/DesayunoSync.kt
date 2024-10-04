package dev.joseluisgs.sync

import dev.joseluisgs.alimentos.*
import java.util.concurrent.TimeUnit

// Estas clases están intencionalmente vacías para el propósito de este ejemplo. Son simplemente clases marcadoras
// para el propósito de la demostración, no contienen propiedades y no sirven para otro propósito.


fun main(args: Array<String>) {
    val timeInit = System.currentTimeMillis()

    val taza = servirCafe()
    println("El café está listo")

    val huevos = freirHuevos(2)
    println("Los huevos están listos")

    val beicon = freirBeicon(3)
    println("El beicon está listo")

    val tostada = tostarPan(2)
    ponerMantequilla(tostada)
    ponerMermelada(tostada)
    println("Las tostadas están listas")

    val zumoNaranja = servirZumoNaranja()
    println("El zumo de naranja está listo")
    println("¡El desayuno está listo!")

    val timeEnd = System.currentTimeMillis()
    println("Tiempo total: ${timeEnd - timeInit} ms")
}

private fun servirZumoNaranja(): Zumo {
    println("Sirviendo zumo de naranja")
    return Zumo()
}

private fun ponerMermelada(tostada: Tostada) {
    println("Poniendo mermelada en la tostada")
}

private fun ponerMantequilla(tostada: Tostada) {
    println("Poniendo mantequilla en la tostada")
}

private fun tostarPan(rebanadas: Int): Tostada {
    for (rebanada in 0..<rebanadas) {
        println("Poniendo una rebanada de pan en la tostadora")
    }
    println("Comenzando a tostar...")
    try {
        TimeUnit.SECONDS.sleep(3) // Simula la espera
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    println("sacando la tostada de la tostadora")

    return Tostada()
}

private fun freirBeicon(lonchas: Int): Beicon {
    println("Poniendo $lonchas lonchas de beicon en la sartén")
    println("Cocinando la primera loncha del beicon...")
    try {
        TimeUnit.SECONDS.sleep(3) // Simula la espera
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    for (rebanada in 0..<lonchas) {
        println("Dando la vuelta a una loncha de beicon")
    }
    println("Cocinando el segundo loncha del beicon...")
    try {
        TimeUnit.SECONDS.sleep(3) // Simula la espera
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    println("Poniendo el beicon en el plato")

    return Beicon()
}

private fun freirHuevos(cantidad: Int): Huevo {
    println("Calentando la sartén para los huevos...")
    try {
        TimeUnit.SECONDS.sleep(3) // Simula la espera
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    println("Rompiendo $cantidad huevos")
    println("Cocinando los huevos...")
    try {
        TimeUnit.SECONDS.sleep(3) // Simula la espera
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    println("Poniendo los huevos en el plato")

    return Huevo()
}

private fun servirCafe(): Cafe {
    println("Sirviendo café")
    return Cafe()
}

