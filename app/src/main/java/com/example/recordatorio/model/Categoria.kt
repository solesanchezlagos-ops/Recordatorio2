package com.example.recordatorio.model

/**
 * Data class inmutable para representar las categorías de la aplicación.
 * Incluye validación de inicialización para evitar nombres vacíos o inválidos.
 */
data class Categoria(
    val id: String,
    val nombre: String,
    val descripcion: String? = null
) {
    init {
        require(id.isNotBlank()) { "El ID de la categoría no puede estar vacío." }
        require(nombre.isNotBlank()) { "El nombre de la categoría no puede estar vacío ni contener solo espacios." }
    }
}
