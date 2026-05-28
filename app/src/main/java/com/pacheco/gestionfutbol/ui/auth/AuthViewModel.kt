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
    object Idle : AuthState() // Estado inicial, no hace nada
    object Loading : AuthState() // Mostraremos un CircularProgressIndicator
    data class Success(val usuario: Usuario) : AuthState() // Login o registro exitoso
    data class Error(val message: String) : AuthState() // Mostraremos un SnackBar o Text de error
}

class AuthViewModel : ViewModel() {


    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)

    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val api = RetrofitClient.apiService


    fun login(correo: String, contrasenia: String) {

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {

                val usuarios = api.getUsuarios()
                val userEncontrado = usuarios.find { it.correo == correo && it.contrasenia == contrasenia }

                if (userEncontrado != null) {
                    _authState.value = AuthState.Success(userEncontrado)
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


                val response = api.createUsuario(nuevoUsuario)
                _authState.value = AuthState.Success(response)

            } catch (e: Exception) {
                _authState.value = AuthState.Error("Error al registrar: ${e.message}")
            }
        }
    }


    fun resetState() {
        _authState.value = AuthState.Idle
    }
}