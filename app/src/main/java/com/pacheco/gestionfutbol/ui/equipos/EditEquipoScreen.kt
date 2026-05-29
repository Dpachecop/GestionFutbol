package com.pacheco.gestionfutbol.ui.equipos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pacheco.gestionfutbol.domain.Equipo

@Composable
fun EditEquipoScreen(
    equipoActual: Equipo,
    viewModel: EquiposViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    var nombre by remember { mutableStateOf(equipoActual.nombreEquipo) }
    var eslogan by remember { mutableStateOf(equipoActual.eslogan) }
    var tecnico by remember { mutableStateOf(equipoActual.tecnico) }
    var pais by remember { mutableStateOf(equipoActual.pais) }
    var ciudad by remember { mutableStateOf(equipoActual.ciudad) }
    var categoria by remember { mutableStateOf(equipoActual.categoria) }

    var goles by remember { mutableStateOf(equipoActual.goles.toString()) }
    var partidosJugados by remember { mutableStateOf(equipoActual.partidosJugados.toString()) }
    var ganados by remember { mutableStateOf(equipoActual.ganados.toString()) }
    var campeonatos by remember { mutableStateOf(equipoActual.campeonatos.toString()) }
    var expulsiones by remember { mutableStateOf(equipoActual.expulsiones.toString()) }
    var empates by remember { mutableStateOf(equipoActual.empates) }

    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Editar Equipo", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre del Equipo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = eslogan, onValueChange = { eslogan = it }, label = { Text("Eslogan") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tecnico, onValueChange = { tecnico = it }, label = { Text("Técnico") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = pais, onValueChange = { pais = it }, label = { Text("País") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = ciudad, onValueChange = { ciudad = it }, label = { Text("Ciudad") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoría") }, modifier = Modifier.fillMaxWidth())

        OutlinedTextField(value = goles, onValueChange = { goles = it }, label = { Text("Goles") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        OutlinedTextField(value = partidosJugados, onValueChange = { partidosJugados = it }, label = { Text("Partidos Jugados") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        OutlinedTextField(value = ganados, onValueChange = { ganados = it }, label = { Text("Partidos Ganados") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        OutlinedTextField(value = campeonatos, onValueChange = { campeonatos = it }, label = { Text("Campeonatos") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        OutlinedTextField(value = expulsiones, onValueChange = { expulsiones = it }, label = { Text("Expulsiones") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        OutlinedTextField(value = empates, onValueChange = { empates = it }, label = { Text("Empates") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val equipoEditado = equipoActual.copy(
                    nombreEquipo = nombre,
                    eslogan = eslogan,
                    tecnico = tecnico,
                    pais = pais,
                    ciudad = ciudad,
                    categoria = categoria,
                    goles = goles.toIntOrNull() ?: 0,
                    partidosJugados = partidosJugados.toIntOrNull() ?: 0,
                    ganados = ganados.toIntOrNull() ?: 0,
                    campeonatos = campeonatos.toIntOrNull() ?: 0,
                    expulsiones = expulsiones.toIntOrNull() ?: 0,
                    empates = empates
                )
                viewModel.updateEquipo(equipoActual.id, equipoEditado)
                onNavigateBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Actualizar Equipo")
        }
    }
}