package com.pacheco.gestionfutbol.ui.equipos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pacheco.gestionfutbol.data.RetrofitClient
import com.pacheco.gestionfutbol.domain.Equipo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class EquiposState {
    object Loading : EquiposState()
    data class Success(val equipos: List<Equipo>) : EquiposState()
    data class Error(val message: String) : EquiposState()
}

class EquiposViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<EquiposState>(EquiposState.Loading)
    val uiState = _uiState.asStateFlow()
    private val api = RetrofitClient.apiService

    init { fetchEquipos() }

    fun fetchEquipos() {
        viewModelScope.launch {
            _uiState.value = EquiposState.Loading
            try { _uiState.value = EquiposState.Success(api.getEquipos()) }
            catch (e: Exception) { _uiState.value = EquiposState.Error(e.message ?: "Error") }
        }
    }

    fun createEquipo(equipo: Equipo) {
        viewModelScope.launch {
            try {
                api.createEquipo(equipo)
                fetchEquipos()
            }
            catch (e: Exception) { _uiState.value = EquiposState.Error(e.message ?: "Error") }
        }
    }

    fun deleteEquipo(id: String) {
        viewModelScope.launch {
            try {
                api.deleteEquipo(id)
                fetchEquipos()
            }
            catch (e: Exception) { _uiState.value = EquiposState.Error(e.message ?: "Error") }
        }
    }
}