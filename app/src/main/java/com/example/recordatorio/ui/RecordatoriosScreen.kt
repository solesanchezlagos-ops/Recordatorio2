package com.example.recordatorio.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recordatorio.data.SimulatedData
import com.example.recordatorio.model.Categoria
import com.example.recordatorio.model.Prioridad
import com.example.recordatorio.ui.components.FilterSection
import com.example.recordatorio.ui.components.RecordatorioCard
import com.example.recordatorio.ui.components.SummaryCard

import androidx.compose.ui.tooling.preview.Preview
import com.example.recordatorio.ui.theme.RecordatorioTheme

/**
 * Pantalla principal de la aplicación de Recordatorios Contextuales.
 * Muestra el resumen estadístico, filtros por prioridad/estado/categoría y la lista de recordatorios.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordatoriosScreen(
    modifier: Modifier = Modifier
) {
    // Estados de UI para búsqueda y filtros
    var searchQuery by remember { mutableStateOf("") }
    var prioridadSeleccionada by remember { mutableStateOf<Prioridad?>(null) }
    var estadoSeleccionado by remember { mutableStateOf("Todos") }
    var categoriaSeleccionada by remember { mutableStateOf<Categoria?>(null) }

    // Obtención de datos filtrados mediante las funciones de SimulatedData
    val recordatoriosFiltrados = remember(
        searchQuery,
        prioridadSeleccionada,
        estadoSeleccionado,
        categoriaSeleccionada
    ) {
        SimulatedData.filtrarRecordatorios(
            prioridadFiltro = prioridadSeleccionada,
            estadoFiltro = estadoSeleccionado,
            categoriaFiltro = categoriaSeleccionada,
            query = searchQuery
        )
    }

    val estadisticas = remember { SimulatedData.obtenerEstadisticas() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Recordatorios Regionales",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Etapa 1 - Modelo & UI Inicial",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // 1. Tarjeta de Resumen Estadístico
            item {
                SummaryCard(estadisticas = estadisticas)
            }

            // 2. Sección de Filtros y Búsqueda
            item {
                FilterSection(
                    searchQuery = searchQuery,
                    onQueryChange = { searchQuery = it },
                    prioridadSeleccionada = prioridadSeleccionada,
                    onPrioridadSelect = { prioridadSeleccionada = it },
                    estadoSeleccionado = estadoSeleccionado,
                    onEstadoSelect = { estadoSeleccionado = it },
                    categoriaSeleccionada = categoriaSeleccionada,
                    onCategoriaSelect = { categoriaSeleccionada = it }
                )
            }

            // 3. Encabezado de la lista con conteo
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Lista de Recordatorios",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${recordatoriosFiltrados.size} de ${SimulatedData.recordatoriosList.size}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // 4. Lista o Estado Vacío
            if (recordatoriosFiltrados.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Sin resultados",
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "No se encontraron recordatorios con los filtros seleccionados.",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(
                    items = recordatoriosFiltrados,
                    key = { it.id }
                ) { recordatorio ->
                    RecordatorioCard(recordatorio = recordatorio)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordatoriosScreenPreview() {
    RecordatorioTheme {
        RecordatoriosScreen()
    }
}
