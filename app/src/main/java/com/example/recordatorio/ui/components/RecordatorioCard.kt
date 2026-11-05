package com.example.recordatorio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recordatorio.data.SimulatedData
import com.example.recordatorio.model.EstadoRecordatorio
import com.example.recordatorio.model.Recordatorio

/**
 * Componente UI para presentar un Recordatorio en la lista.
 * Muestra título, categoría (con color del Map), prioridad, estado y datos opcionales (ubicación, aviso, vencimiento).
 */
@Composable
fun RecordatorioCard(
    recordatorio: Recordatorio,
    modifier: Modifier = Modifier
) {
    val configCat = SimulatedData.categoriaStyleMap[recordatorio.categoria]
    val colorCategoria = configCat?.colorHex?.let { Color(it) } ?: MaterialTheme.colorScheme.primary
    val colorPrioridad = Color(recordatorio.prioridad.colorHex)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Encabezado: Categoría (Badge con estilo del Map) + Badge de Prioridad
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Badge de Categoría
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = colorCategoria.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = configCat?.etiqueta ?: recordatorio.categoria.nombre,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorCategoria,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Badge de Prioridad
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(colorPrioridad)
                    )
                    Text(
                        text = "Prioridad ${recordatorio.prioridad.nombre}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorPrioridad
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Título
            Text(
                text = recordatorio.titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Descripción (opcional)
            recordatorio.descripcion?.let { desc ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Estado del recordatorio
            EstadoBadge(estado = recordatorio.estado)

            // Datos Opcionales: Ubicación, Aviso, Fecha de Vencimiento
            if (recordatorio.ubicacion != null || recordatorio.aviso != null || recordatorio.fechaVencimiento != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    recordatorio.ubicacion?.let { loc ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = "Ubicación",
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = loc,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    recordatorio.aviso?.let { av ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Aviso",
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = av,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    recordatorio.fechaVencimiento?.let { fecha ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Vencimiento",
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Vence: $fecha",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EstadoBadge(estado: EstadoRecordatorio) {
    val (colorFondo, colorTexto, labelText) = when (estado) {
        is EstadoRecordatorio.Pendiente -> Triple(
            Color(0xFFFFF3E0),
            Color(0xFFE65100),
            "Pendiente"
        )
        is EstadoRecordatorio.EnProceso -> Triple(
            Color(0xFFE3F2FD),
            Color(0xFF1565C0),
            "En Proceso"
        )
        is EstadoRecordatorio.Completado -> Triple(
            Color(0xE8E8F5E9),
            Color(0xFF2E7D32),
            "Completado ${estado.fechaFinalizacion?.let { "($it)" } ?: ""}"
        )
        is EstadoRecordatorio.Archivado -> Triple(
            Color(0xFFF5F5F5),
            Color(0xFF616161),
            "Archivado"
        )
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = colorFondo
    ) {
        Text(
            text = labelText,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorTexto,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}
