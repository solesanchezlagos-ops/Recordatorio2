package com.example.recordatorio.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recordatorio.data.SimulatedData
import com.example.recordatorio.model.Categoria
import com.example.recordatorio.model.EstadoRecordatorio
import com.example.recordatorio.model.Prioridad

/**
 * Sección de búsqueda y filtros dinámicos (Búsqueda por texto, Prioridades, Estados y Categorías).
 */
@Composable
fun FilterSection(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    prioridadSeleccionada: Prioridad?,
    onPrioridadSelect: (Prioridad?) -> Unit,
    estadoSeleccionado: String,
    onEstadoSelect: (String) -> Unit,
    categoriaSeleccionada: Categoria?,
    onCategoriaSelect: (Categoria?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Campo de Búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Buscar recordatorio o ubicación...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Limpiar"
                        )
                    }
                }
            },
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Filtro por Prioridad
        Text(
            text = "Prioridad:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = prioridadSeleccionada == null,
                onClick = { onPrioridadSelect(null) },
                label = { Text("Todas") }
            )
            Prioridad.todas.forEach { prioridad ->
                FilterChip(
                    selected = prioridadSeleccionada == prioridad,
                    onClick = {
                        onPrioridadSelect(if (prioridadSeleccionada == prioridad) null else prioridad)
                    },
                    label = { Text(prioridad.nombre) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(prioridad.colorHex).copy(alpha = 0.2f),
                        selectedLabelColor = Color(prioridad.colorHex)
                    )
                )
            }
        }

        // Filtro por Estado
        Text(
            text = "Estado:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val opcionesEstado = listOf("Todos") + EstadoRecordatorio.obtenerNombres()
            opcionesEstado.forEach { estado ->
                FilterChip(
                    selected = estadoSeleccionado == estado,
                    onClick = { onEstadoSelect(estado) },
                    label = { Text(estado) }
                )
            }
        }

        // Filtro por Categorías (Demuestra el Set y Map de Categorías)
        Text(
            text = "Categoría:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = categoriaSeleccionada == null,
                onClick = { onCategoriaSelect(null) },
                label = { Text("Todas") }
            )
            SimulatedData.categoriasSet.forEach { categoria ->
                val config = SimulatedData.categoriaStyleMap[categoria]
                val colorCat = config?.colorHex?.let { Color(it) } ?: MaterialTheme.colorScheme.primary

                FilterChip(
                    selected = categoriaSeleccionada == categoria,
                    onClick = {
                        onCategoriaSelect(if (categoriaSeleccionada == categoria) null else categoria)
                    },
                    label = { Text(categoria.nombre) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = colorCat.copy(alpha = 0.2f),
                        selectedLabelColor = colorCat
                    )
                )
            }
        }
    }
}
