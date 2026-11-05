package com.example.recordatorio.model

/**
 * Data class inmutable que representa un recordatorio dentro del sistema regional.
 * Contempla atributos obligatorios y opcionales (ubicación, aviso, fecha de vencimiento).
 * Incluye validaciones para garantizar la integridad del modelo de datos.
 */
data class Recordatorio(
    val id: String,
    val titulo: String,
    val descripcion: String? = null,
    val categoria: Categoria,
    val prioridad: Prioridad,
    val estado: EstadoRecordatorio = EstadoRecordatorio.Pendiente,
    val fechaVencimiento: String? = null,
    val ubicacion: String? = null,
    val aviso: String? = null
) {
    init {
        require(id.isNotBlank()) { "El ID del recordatorio no puede estar vacío." }
        require(titulo.isNotBlank()) { "El título del recordatorio no puede estar vacío." }
    }
}
