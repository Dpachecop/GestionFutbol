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
fun CreateEquipoScreen(
    viewModel: EquiposViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    // Campos de texto
    var nombre by remember { mutableStateOf("") }
    var eslogan by remember { mutableStateOf("") }
    var tecnico by remember { mutableStateOf("") }
    var pais by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }

    // Campos numéricos
    var goles by remember { mutableStateOf("") }
    var partidosJugados by remember { mutableStateOf("") }
    var ganados by remember { mutableStateOf("") }
    var campeonatos by remember { mutableStateOf("") }
    var expulsiones by remember { mutableStateOf("") }
    var empates by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Nuevo Equipo", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre del Equipo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = eslogan, onValueChange = { eslogan = it }, label = { Text("Eslogan") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tecnico, onValueChange = { tecnico = it }, label = { Text("Técnico") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = pais, onValueChange = { pais = it }, label = { Text("País") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = ciudad, onValueChange = { ciudad = it }, label = { Text("Ciudad") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoría") }, modifier = Modifier.fillMaxWidth())

        // Campos numéricos con teclado tipo Number
        OutlinedTextField(value = goles, onValueChange = { goles = it }, label = { Text("Goles") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        OutlinedTextField(value = partidosJugados, onValueChange = { partidosJugados = it }, label = { Text("Partidos Jugados") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        OutlinedTextField(value = ganados, onValueChange = { ganados = it }, label = { Text("Partidos Ganados") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        OutlinedTextField(value = campeonatos, onValueChange = { campeonatos = it }, label = { Text("Campeonatos") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        OutlinedTextField(value = expulsiones, onValueChange = { expulsiones = it }, label = { Text("Expulsiones") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        OutlinedTextField(value = empates, onValueChange = { empates = it }, label = { Text("Empates") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val nuevoEquipo = Equipo(
                    id = "",
                    createdAt = "",
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
                viewModel.createEquipo(nuevoEquipo)
                onNavigateBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Equipo")
        }
    }
}