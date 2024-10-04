package dev.joseluisgs


import java.util.concurrent.LinkedBlockingQueue

// Soy un monitor, controlo el acceso a un recurso compartido
// Ojo que no falla si se bloquea, es que nunca se puede terminar!
// Lo hemos arreglado con un contador de pelotas consumidas

class ProductorConsumidorColecciones {
    private val buffer = LinkedBlockingQueue<Pelota>(10) // Buffer con tamaño máximo de 10

    fun producir(pelota: Pelota) {
        buffer.put(pelota) // put() maneja la sincronización y espera si el buffer está lleno
        println("Producido: $pelota")
    }

    fun consumir(): Pelota {
        val pelota = buffer.take() // take() maneja la sincronización y espera si el buffer está vacío
        println("Consumido: $pelota")
        return pelota
    }
}


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

    val consumidores = List(5) { consumidorId ->
        Thread {
            repeat(2) {
                productorConsumidor.consumir()
                Thread.sleep(150)
            }
            // Si salgo del aquí, los consumidores no terminan
        }
    }

    productores.forEach { it.start() }
    consumidores.forEach { it.start() }

    productores.forEach { it.join() }
    consumidores.forEach { it.join() }

    println("Todos los productores y consumidores han terminado.")
}