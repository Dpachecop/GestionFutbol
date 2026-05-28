package com.pacheco.gestionfutbol.ui.equipos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    onLogout: () -> Unit // Callback para cerrar sesión
) {
    val uiState by viewModel.uiState.collectAsState()

    // El Scaffold es igual al de Flutter: nos da estructura (Appbar, FAB, Body)
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
        }
    ) { paddingValues ->
        // Contenedor principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is EquiposState.Loading -> {
                    CircularProgressIndicator()
                }
                is EquiposState.Success -> {
                    val equipos = (uiState as EquiposState.Success).equipos
                    if (equipos.isEmpty()) {
                        Text("No hay equipos registrados.")
                    } else {
                        // Tu equivalente a ListView.builder
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre tarjetas
                        ) {
                            // Iteramos sobre la lista de equipos
                            items(equipos) { equipo ->
                                EquipoItemCard(equipo = equipo)
                            }
                        }
                    }
                }
                is EquiposState.Error -> {
                    val errorMsg = (uiState as EquiposState.Error).message
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: $errorMsg", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchEquipos() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }
    }
}

// Sub-componente para dibujar cada tarjeta de equipo (Tu "ListTile" personalizado)
@Composable
fun EquipoItemCard(equipo: Equipo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = equipo.nombreEquipo.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Eslogan: ${equipo.eslogan}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Técnico: ${equipo.tecnico}")
            Text(text = "País/Ciudad: ${equipo.pais} - ${equipo.ciudad}")
            Text(text = "Categoría: ${equipo.categoria}")

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            // Fila para las estadísticas
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