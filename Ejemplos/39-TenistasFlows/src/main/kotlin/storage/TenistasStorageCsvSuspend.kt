package dev.joseluisgs.storage

import dev.joseluisgs.models.Tenista
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDate

/**
 * Implementación de la serialización de Tenista en CSV asíncrono en base a Flow
 */
class TenistasStorageCsv {
    private val logger = logging()


    fun import(file: File): Flow<Tenista> = flow {
        logger.debug { "Importando Tenistas desde CSV: $file" }
        delay(20) // Simulamos una carga lenta

        file.bufferedReader().useLines { lines ->
            lines.drop(1).forEach { line ->
                val tenista = parseLine(line.split(","))
                logger.debug { "Emitiendo Tenista: $tenista" }
                emit(tenista)
                delay(10) // Simulamos un pequeño retraso por cada línea procesada
            }
        }
    }.flowOn(Dispatchers.IO)


    private fun parseLine(parts: List<String>): Tenista {
        //logger.debug { "Parseando línea: $parts" }
        return Tenista(
            id = parts[0].toLong(),
            nombre = parts[1],
            pais = parts[2],
            altura = parts[3].toInt(),
            peso = parts[4].toInt(),
            puntos = parts[5].toInt(),
            mano = Tenista.Mano.valueOf(parts[6]),
            fechaNacimiento = LocalDate.parse(parts[7])
        )
    }
}