package dev.joseluisgs

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.system.exitProcess

// Soy un monitor, controlo el acceso a un recurso compartido
// Ojo que no falla si se bloquea, es que nunca se puede terminar!
// Lo hemos arreglado con un contador de pelotas consumidas
class ProductorConsumidor {
    private val buffer = mutableListOf<Pelota>() // Buffer
    private val lock: Lock = ReentrantLock() // Cerrojo
    private val notFull: Condition = lock.newCondition() // Condición
    private val notEmpty: Condition = lock.newCondition() // Condición
    private val maxSize = 10 // Tamaño del buffer
    val pelotasConsumidas = AtomicInteger(0) // Pelotas consumidas

    fun producir(pelota: Pelota) {
        lock.withLock {
            while (buffer.size == maxSize) {
                notFull.await()
            }
            buffer.add(pelota)
            println("Producido: $pelota")
            notEmpty.signalAll()
        }
    }

    fun consumir(): Pelota {
        lock.withLock {
            while (buffer.isEmpty()) {
                notEmpty.await()
            }
            val pelota = buffer.removeAt(0)
            pelotasConsumidas.incrementAndGet()
            println("Consumido: $pelota")
            notFull.signalAll()
            return pelota
        }
    }
}

data class Pelota(val id: Int)

fun main() {
    val productorConsumidor = ProductorConsumidor()

    val productores = List(2) { productorId ->
        Thread {
            repeat(5) {
                productorConsumidor.producir(Pelota(it + productorId * 5))
                Thread.sleep(100)
            }
        }
    }

    val consumidores = List(4) { consumidorId ->
        Thread {
            repeat(3) {
                productorConsumidor.consumir()
                if (productorConsumidor.pelotasConsumidas.get() == 10) {
                    // println("Consumidor $consumidorId ha terminado")
                    // return@Thread
                    println("Ya se han consumido todas las pelotas, no se consumen más. Adiós.")
                    exitProcess(0)
                }
                println("Se han consumido ${productorConsumidor.pelotasConsumidas.get()} pelotas")
                Thread.sleep(150)
            }
        }
    }

    productores.forEach { it.start() }
    consumidores.forEach { it.start() }

    //productores.forEach { it.join() }
    // consumidores.forEach { it.join() }

    println("Todos los productores y consumidores han terminado.")
}
