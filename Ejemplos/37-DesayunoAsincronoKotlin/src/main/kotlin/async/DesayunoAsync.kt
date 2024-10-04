package dev.joseluisgs.async

import dev.joseluisgs.alimentos.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val timeInit = System.currentTimeMillis()

    val taza = servirCafe()
    println("El café está listo")

    // Lanzamos las corrutinas para las tareas asíncronas
    val huevosTask = async { freirHuevosAsync(2) }
    val beiconTask = async { freirBeiconAsync(3) }
    val tostadaTask = async { prepararTostadaConMantequillaYMermeladaAsync(2) }

    // Esperamos a que todas las tareas terminen
    val results = awaitAll(huevosTask, beiconTask, tostadaTask)

    results.forEach {
        when (it) {
            is Huevo -> println("Los huevos están listos")
            is Beicon -> println("El beicon está listo")
            is Tostada -> println("Las tostadas están listas")
        }
    }

    val zumoNaranja = servirZumoNaranja()
    println("El zumo de naranja está listo")
    println("¡El desayuno está listo!")

    val timeEnd = System.currentTimeMillis()
    println("Tiempo total: ${timeEnd - timeInit} ms")
}

private suspend fun prepararTostadaConMantequillaYMermeladaAsync(rebanadas: Int): Tostada {
    val tostada = tostarPanAsync(rebanadas) // no es necesario el await
    ponerMantequilla(tostada)
    ponerMermelada(tostada)
    return tostada
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

private suspend fun tostarPanAsync(rebanadas: Int): Tostada {
    for (rebanada in 0 until rebanadas) {
        println("Poniendo una rebanada de pan en la tostadora")
    }
    println("Comenzando a tostar...")
    delay(3000) // Simula la espera
    println("Sacando la tostada de la tostadora")
    return Tostada()
}

private suspend fun freirBeiconAsync(lonchas: Int): Beicon {
    println("Poniendo $lonchas lonchas de beicon en la sartén")
    println("Cocinando la primera loncha del beicon...")
    delay(3000) // Simula la espera
    for (rebanada in 0 until lonchas) {
        println("Dando la vuelta a una loncha de beicon")
    }
    println("Cocinando el segundo loncha del beicon...")
    delay(3000) // Simula la espera
    println("Poniendo el beicon en el plato")
    return Beicon()
}

private suspend fun freirHuevosAsync(cantidad: Int): Huevo {
    println("Calentando la sartén para los huevos...")
    delay(3000) // Simula la espera
    println("Rompiendo $cantidad huevos")
    println("Cocinando los huevos...")
    delay(3000) // Simula la espera
    println("Poniendo los huevos en el plato")
    return Huevo()
}

private fun servirCafe(): Cafe {
    println("Sirviendo café")
    return Cafe()
}