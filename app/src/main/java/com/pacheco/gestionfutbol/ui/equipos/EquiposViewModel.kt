package com.pacheco.gestionfutbol.ui.equipos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pacheco.gestionfutbol.data.RetrofitClient
import com.pacheco.gestionfutbol.domain.Equipo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Estados posibles para nuestra pantalla de lista
sealed class EquiposState {
    object Loading : EquiposState()
    data class Success(val equipos: List<Equipo>) : EquiposState()
    data class Error(val message: String) : EquiposState()
}

class EquiposViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<EquiposState>(EquiposState.Loading)
    val uiState: StateFlow<EquiposState> = _uiState.asStateFlow()

    private val api = RetrofitClient.apiService

    // El equivalente a initState() de Flutter. Se ejecuta al instanciar la clase.
    init {
        fetchEquipos()
    }

    // Función para obtener la lista (es pública por si queremos poner un botón de recargar)
    fun fetchEquipos() {
        viewModelScope.launch {
            _uiState.value = EquiposState.Loading
            try {
                // Hacemos el GET al endpoint /equipos
                val response = api.getEquipos()
                _uiState.value = EquiposState.Success(response)
            } catch (e: Exception) {
                _uiState.value = EquiposState.Error(e.message ?: "Error de conexión")
            }
        }
    }
}