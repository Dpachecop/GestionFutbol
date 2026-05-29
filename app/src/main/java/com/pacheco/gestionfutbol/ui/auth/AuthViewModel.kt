package com.pacheco.gestionfutbol.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pacheco.gestionfutbol.data.RetrofitClient
import com.pacheco.gestionfutbol.domain.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val usuario: Usuario) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val api = RetrofitClient.apiService

    fun login(correo: String, contrasenia: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                // Usamos el método optimizado que creamos en ApiService
                val usuarios = api.loginUsuario(correo, contrasenia)

                if (usuarios.isNotEmpty()) {
                    _authState.value = AuthState.Success(usuarios.first())
                } else {
                    _authState.value = AuthState.Error("Credenciales incorrectas")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Error de red: ${e.message}")
            }
        }
    }

    fun register(usuario: String, correo: String, contrasenia: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val nuevoUsuario = Usuario(
                    id = "",
                    createdAt = "",
                    usuario = usuario,
                    correo = correo,
                    contrasenia = contrasenia
                )
                _authState.value = AuthState.Success(api.createUsuario(nuevoUsuario))
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Error al registrar: ${e.message}")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}