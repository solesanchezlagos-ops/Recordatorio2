package com.example.recordatorio.data

import com.example.recordatorio.model.Categoria
import com.example.recordatorio.model.EstadoRecordatorio
import com.example.recordatorio.model.Prioridad
import com.example.recordatorio.model.Recordatorio

/**
 * Configuración visual asociada a una categoría mediante un Map.
 */
data class ConfigCategoria(
    val etiqueta: String,
    val colorHex: Long
)

/**
 * Proveedor de datos simulados y repositorio inicial para la gestión de recordatorios.
 * Aplica el uso explícito de Lists, Sets y Maps según los requerimientos.
 */
object SimulatedData {

    // 1. SET: Colección de categorías únicas (evita duplicados)
    val categoriasSet: Set<Categoria> = setOf(
        Categoria("CAT-01", "Infraestructura", "Mantenimiento e inspección de obras públicas"),
        Categoria("CAT-02", "Transporte", "Gestión de rutas y flota regional"),
        Categoria("CAT-03", "Salud", "Supervisión de centros de atención primaria"),
        Categoria("CAT-04", "Educación", "Proyectos y recursos para escuelas regionales"),
        Categoria("CAT-05", "Administración", "Trámites, licencias y reuniones operativas"),
        Categoria("CAT-01", "Infraestructura", "Categoría duplicada que el Set omitirá") // Demuestra la propiedad del Set
    )

    // Referencias rápidas a categorías del Set
    private val catInfra = categoriasSet.first { it.id == "CAT-01" }
    private val catTrans = categoriasSet.first { it.id == "CAT-02" }
    private val catSalud = categoriasSet.first { it.id == "CAT-03" }
    private val catEduc = categoriasSet.first { it.id == "CAT-04" }
    private val catAdmin = categoriasSet.first { it.id == "CAT-05" }

    // 2. MAP: Asocia cada categoría con una etiqueta y color representativo
    val categoriaStyleMap: Map<Categoria, ConfigCategoria> = mapOf(
        catInfra to ConfigCategoria("Infraestructura Regional", 0xFF1E88E5L), // Azul
        catTrans to ConfigCategoria("Transporte & Logística", 0xFFD81B60L),  // Rosa/Magenta
        catSalud to ConfigCategoria("Salud Pública", 0xFF43A047L),           // Verde
        catEduc to ConfigCategoria("Educación & Cultura", 0xFF8E24AAL),      // Púrpura
        catAdmin to ConfigCategoria("Gestión Administrativa", 0xFFFB8C00L)   // Ámbar
    )

    // 3. LIST: Almacenamiento principal de los recordatorios simulados
    val recordatoriosList: List<Recordatorio> = listOf(
        Recordatorio(
            id = "REC-101",
            titulo = "Inspección de puente sobre Río Central",
            descripcion = "Verificar estado estructural y señalización de emergencia tras lluvias intensas.",
            categoria = catInfra,
            prioridad = Prioridad.Alta,
            estado = EstadoRecordatorio.Pendiente,
            fechaVencimiento = "28 Feb 2026",
            ubicacion = "Puente Perquilauquen, Km 352 Ruta 5",
            aviso = "Aviso prioritario a equipo técnico a las 08:00 AM"
        ),
        Recordatorio(
            id = "REC-102",
            titulo = "Coordinación de buses de transporte escolar",
            descripcion = "Revisión de licencias y rutas asignadas para el ciclo lectivo rural.",
            categoria = catTrans,
            prioridad = Prioridad.Media,
            estado = EstadoRecordatorio.EnProceso,
            fechaVencimiento = "05 Mar 2026",
            ubicacion = "Terminal Regional de Pasajeros",
            aviso = "Recordatorio 24h antes del inicio"
        ),
        Recordatorio(
            id = "REC-103",
            titulo = "Auditoría de insumos médicos en CESFAM Ñiquén",
            descripcion = "Confirmar stock de vacunas y materiales de bioseguridad.",
            categoria = catSalud,
            prioridad = Prioridad.Alta,
            estado = EstadoRecordatorio.Pendiente,
            fechaVencimiento = "01 Mar 2026",
            ubicacion = "Centro de Salud Familiar Ñiquén",
            aviso = "Alerta SMS al supervisor sanitario"
        ),
        Recordatorio(
            id = "REC-104",
            titulo = "Entrega de equipamiento informático para escuelas",
            descripcion = "Distribución de 50 computadores portátiles en zonas rurales.",
            categoria = catEduc,
            prioridad = Prioridad.Media,
            estado = EstadoRecordatorio.Completado(fechaFinalizacion = "20 Feb 2026"),
            fechaVencimiento = "20 Feb 2026",
            ubicacion = "Liceo Bicentenario Ñiquén",
            aviso = null
        ),
        Recordatorio(
            id = "REC-105",
            titulo = "Renovación de patentes y licencias municipales",
            descripcion = "Presentación de documentación contable ante el municipio comunal.",
            categoria = catAdmin,
            prioridad = Prioridad.Baja,
            estado = EstadoRecordatorio.Pendiente,
            fechaVencimiento = "15 Mar 2026",
            ubicacion = "Edificio Municipalidad Ñiquén, Piso 2",
            aviso = "Aviso automático en calendario"
        ),
        Recordatorio(
            id = "REC-106",
            titulo = "Mantenimiento preventivo de servidores regionales",
            descripcion = "Actualización de parches de seguridad y copia de respaldo del sistema.",
            categoria = catAdmin,
            prioridad = Prioridad.Alta,
            estado = EstadoRecordatorio.EnProceso,
            fechaVencimiento = "02 Mar 2026",
            ubicacion = "Centro de Datos Institucional",
            aviso = "Notificación a usuarios sobre ventana de mantenimiento"
        ),
        Recordatorio(
            id = "REC-107",
            titulo = "Capacitación de emergencias para personal de transporte",
            descripcion = "Taller presencial sobre primeros auxilios y evacuación rápida.",
            categoria = catTrans,
            prioridad = Prioridad.Baja,
            estado = EstadoRecordatorio.Archivado,
            fechaVencimiento = "10 Feb 2026",
            ubicacion = "Auditorio Regional de Seguridad",
            aviso = null
        )
    )

    // 4. FUNCIONES DE FILTRADO Y MAPEO DE ESTADÍSTICAS

    /**
     * Filtra la lista de recordatorios por prioridad, estado y término de búsqueda.
     */
    fun filtrarRecordatorios(
        lista: List<Recordatorio> = recordatoriosList,
        prioridadFiltro: Prioridad? = null,
        estadoFiltro: String? = null,
        categoriaFiltro: Categoria? = null,
        query: String = ""
    ): List<Recordatorio> {
        return lista.filter { item ->
            val coincidePrioridad = prioridadFiltro == null || item.prioridad == prioridadFiltro
            val coincideEstado = estadoFiltro == null || estadoFiltro == "Todos" || item.estado.etiqueta == estadoFiltro
            val coincideCategoria = categoriaFiltro == null || item.categoria == categoriaFiltro
            val coincideQuery = query.isBlank() ||
                    item.titulo.contains(query, ignoreCase = true) ||
                    (item.descripcion?.contains(query, ignoreCase = true) ?: false) ||
                    (item.ubicacion?.contains(query, ignoreCase = true) ?: false)

            coincidePrioridad && coincideEstado && coincideCategoria && coincideQuery
        }
    }

    /**
     * Genera un Map con contadores estadísticos de los recordatorios.
     */
    fun obtenerEstadisticas(lista: List<Recordatorio> = recordatoriosList): Map<String, Int> {
        return mapOf(
            "Total" to lista.size,
            "Pendientes" to lista.count { it.estado is EstadoRecordatorio.Pendiente },
            "En Proceso" to lista.count { it.estado is EstadoRecordatorio.EnProceso },
            "Completados" to lista.count { it.estado is EstadoRecordatorio.Completado },
            "Alta Prioridad" to lista.count { it.prioridad is Prioridad.Alta },
            "Categorías" to categoriasSet.size
        )
    }
}
