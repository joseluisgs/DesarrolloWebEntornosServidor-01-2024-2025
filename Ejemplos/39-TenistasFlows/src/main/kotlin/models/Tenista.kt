package dev.joseluisgs.models

import java.time.LocalDate

data class Tenista(
    val id: Long = NEW_ID,
    val nombre: String,
    val pais: String,
    val altura: Int,
    val peso: Int,
    val puntos: Int,
    val mano: Mano,
    val fechaNacimiento: LocalDate,
) {

    companion object {
        const val NEW_ID = 0L
    }

    enum class Mano {
        DIESTRO,
        ZURDO
    }

}