package dev.joseluisgs.repository

import dev.joseluisgs.models.Tenista
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import org.lighthousegames.logging.logging

class TenistasRepository {
    private val logger = logging()
    private val db = mutableMapOf<Long, Tenista>()

    // Los stateFlow necesitan un valor inicial
    private val _listaActualizada: MutableStateFlow<List<Tenista>> = MutableStateFlow(emptyList())

    // Los sharedFlow no necesitan un valor inicial, y pueden ser emitidos en cualquier momento, podemos simular un StateFlow con un replay = 1
    private val _notificaciones: MutableSharedFlow<String> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val listaActualizada: StateFlow<List<Tenista>>
        get() = _listaActualizada.asStateFlow()

    val notificaciones: SharedFlow<String>
        get() = _notificaciones.asSharedFlow()

    fun findAll(): List<Tenista> {
        logger.debug { "Buscando todos los Tenistas" }
        return db.values.toList()
    }

    fun findById(id: Long): Tenista? {
        logger.debug { "Buscando Tenista por ID: $id" }
        return db[id]
    }

    suspend fun save(tenista: Tenista): Tenista {
        logger.debug { "Guardando Tenista: $tenista" }
        val id = db.size.toLong() + 1
        val tenistaGuardado = tenista.copy(id = id)
        db[id] = tenistaGuardado
        return tenistaGuardado.also {
            _listaActualizada.value = db.values.toList()
            _notificaciones.emit("Tenista Guardado: $tenistaGuardado")
        }
    }

    suspend fun update(tenista: Tenista): Tenista? {
        logger.debug { "Actualizando Tenista: $tenista" }
        return db.computeIfPresent(tenista.id) { _, _ -> tenista }.also {
            _listaActualizada.value = db.values.toList()
            _notificaciones.emit("Tenista Actualizado: $tenista")
        }
    }

    suspend fun delete(id: Long): Tenista? {
        logger.debug { "Borrando Tenista por ID: $id" }
        if (db.containsKey(id)) {
            val tenista = db.remove(id)
            _listaActualizada.value = db.values.toList()
            _notificaciones.emit("Tenista Borrado: $tenista")
            return tenista
        }
        return null
    }
}