package com.pacheco.gestionfutbol.ui.equipos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pacheco.gestionfutbol.domain.Equipo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquiposScreen(
    viewModel: EquiposViewModel = viewModel(),
    onLogout: () -> Unit,
    onNavigateToCreate: () -> Unit // Nuevo callback para ir a crear equipo
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Equipos") },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Salir", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        },
        // ¡Agregamos el FAB al estilo Flutter!
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Equipo")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is EquiposState.Loading -> CircularProgressIndicator()
                is EquiposState.Success -> {
                    val equipos = (uiState as EquiposState.Success).equipos
                    if (equipos.isEmpty()) {
                        Text("No hay equipos registrados.")
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(equipos) { equipo ->
                                // Pasamos la función de eliminar a la tarjeta
                                EquipoItemCard(
                                    equipo = equipo,
                                    onDeleteClick = { viewModel.deleteEquipo(equipo.id) }
                                )
                            }
                        }
                    }
                }
                is EquiposState.Error -> {
                    val errorMsg = (uiState as EquiposState.Error).message
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: $errorMsg", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchEquipos() }) { Text("Reintentar") }
                    }
                }
            }
        }
    }
}

@Composable
fun EquipoItemCard(
    equipo: Equipo,
    onDeleteClick: () -> Unit // Recibimos el callback aquí
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Usamos un Row para poner el título a la izquierda y el botón de borrar a la derecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = equipo.nombreEquipo.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f) // Para que no empuje el botón
                )
                // Botón de eliminar
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Text(text = "Eslogan: ${equipo.eslogan}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Técnico: ${equipo.tecnico}")
            Text(text = "País/Ciudad: ${equipo.pais} - ${equipo.ciudad}")
            Text(text = "Categoría: ${equipo.categoria}")

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider() // Divider moderno en Material 3
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "PJ: ${equipo.partidosJugados}", fontWeight = FontWeight.SemiBold)
                Text(text = "Goles: ${equipo.goles}", fontWeight = FontWeight.SemiBold)
                Text(text = "Títulos: ${equipo.campeonatos}", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}