import dev.joseluisgs.storage.TenistasStorageCsvSuspend
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.lighthousegames.logging.logging
import java.io.File
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val logger = logging()
    val storage = TenistasStorageCsvSuspend()

    logger.debug { "Cargando datos" }
    val time = measureTimeMillis {
        // paralelizamos la carga de los datos
        val listaFutura1 = async {
            try {
                withTimeout(20) {
                    storage.import(File("data01.csv"))
                }
            } catch (e: TimeoutCancellationException) {
                logger.error { "Timeout al cargar data01.csv: ${e.message}" }
                emptyList() // Devuelve una lista vacía en caso de timeout
            }
        }
        val listaFutura2 = async { storage.import(File("data02.csv")) }

        try {
            logger.debug { "Datos cargados" }
            val listaFinal = listaFutura1.await() + listaFutura2.await()
            // tenista con altura máxima y valor de la misma
            val tenistaMaxAltura = listaFinal.maxByOrNull { it.altura }
            println("Tenista con altura máxima: $tenistaMaxAltura y su altura es ${tenistaMaxAltura?.altura}")
        } catch (e: Exception) {
            logger.error { "Error cargando datos: ${e.message}" }
        }
    }

    println("Código ejecutado en: $time ms")
}
