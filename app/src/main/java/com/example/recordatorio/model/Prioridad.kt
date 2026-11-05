package com.example.recordatorio.model

/**
 * Representa la prioridad de un recordatorio mediante una sealed class.
 * Permite definir una jerarquía inmutable y controlada de prioridades.
 * Almacena el color como valor numérico ARGB (Long) para mantener el modelo desacoplado de la UI.
 */
sealed class Prioridad(
    val nombre: String,
    val nivel: Int,
    val colorHex: Long
) {
    object Alta : Prioridad("Alta", 3, 0xFFD32F2FL)    // Rojo
    object Media : Prioridad("Media", 2, 0xFFF57C00L)   // Naranja/Ámbar
    object Baja : Prioridad("Baja", 1, 0xFF388E3CL)    // Verde

    companion object {
        val todas: List<Prioridad> get() = listOf(Alta, Media, Baja)
    }
}
