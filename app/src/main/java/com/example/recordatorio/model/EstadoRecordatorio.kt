package com.example.recordatorio.model

/**
 * Representa los estados posibles de un recordatorio mediante una sealed class.
 * Permite modelar datos específicos asociados según el estado (por ejemplo, fecha de finalización en Completado).
 */
sealed class EstadoRecordatorio(val etiqueta: String) {
    object Pendiente : EstadoRecordatorio("Pendiente")
    object EnProceso : EstadoRecordatorio("En Proceso")
    data class Completado(val fechaFinalizacion: String? = null) : EstadoRecordatorio("Completado")
    object Archivado : EstadoRecordatorio("Archivado")

    companion object {
        val todos: List<EstadoRecordatorio> get() = listOf(
            Pendiente,
            EnProceso,
            Completado(),
            Archivado
        )

        fun obtenerNombres(): List<String> = listOf(
            "Pendiente",
            "En Proceso",
            "Completado",
            "Archivado"
        )
    }
}
