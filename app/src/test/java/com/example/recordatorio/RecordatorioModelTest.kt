package com.example.recordatorio

import com.example.recordatorio.data.SimulatedData
import com.example.recordatorio.model.Categoria
import com.example.recordatorio.model.EstadoRecordatorio
import com.example.recordatorio.model.Prioridad
import com.example.recordatorio.model.Recordatorio
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RecordatorioModelTest {

    @Test(expected = IllegalArgumentException::class)
    fun `crear recordatorio con titulo vacio lanza excepcion`() {
        Recordatorio(
            id = "REC-001",
            titulo = "   ",
            categoria = Categoria("CAT-01", "Prueba"),
            prioridad = Prioridad.Baja
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `crear categoria con nombre vacio lanza excepcion`() {
        Categoria(id = "CAT-99", nombre = "")
    }

    @Test
    fun `set de categorias evita elementos duplicados`() {
        val cat1 = Categoria("CAT-01", "Infraestructura")
        val cat2 = Categoria("CAT-01", "Infraestructura") // Misma id y nombre
        val set = setOf(cat1, cat2)

        assertEquals(1, set.size)
        assertEquals(5, SimulatedData.categoriasSet.size) // Verifica el Set simulado
    }

    @Test
    fun `map de categorias asocia correctamente estilos y colores`() {
        val categoria = SimulatedData.categoriasSet.first { it.id == "CAT-01" }
        val config = SimulatedData.categoriaStyleMap[categoria]

        assertNotNull(config)
        assertEquals("Infraestructura Regional", config?.etiqueta)
    }

    @Test
    fun `filtrado por prioridad alta retorna unicamente recordatorios de alta prioridad`() {
        val filtrados = SimulatedData.filtrarRecordatorios(prioridadFiltro = Prioridad.Alta)

        assertTrue(filtrados.isNotEmpty())
        assertTrue(filtrados.all { it.prioridad is Prioridad.Alta })
    }

    @Test
    fun `filtrado por estado Pendiente funciona correctamente`() {
        val filtrados = SimulatedData.filtrarRecordatorios(estadoFiltro = "Pendiente")

        assertTrue(filtrados.isNotEmpty())
        assertTrue(filtrados.all { it.estado is EstadoRecordatorio.Pendiente })
    }

    @Test
    fun `estadisticas resumen datos correctamente`() {
        val stats = SimulatedData.obtenerEstadisticas()

        assertEquals(7, stats["Total"])
        assertTrue((stats["Pendientes"] ?: 0) > 0)
        assertEquals(5, stats["Categorías"])
    }
}
