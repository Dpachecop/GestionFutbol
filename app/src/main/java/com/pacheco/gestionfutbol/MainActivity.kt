package com.pacheco.gestionfutbol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.pacheco.gestionfutbol.domain.Equipo
import com.pacheco.gestionfutbol.ui.auth.LoginScreen
import com.pacheco.gestionfutbol.ui.auth.RegisterScreen
import com.pacheco.gestionfutbol.ui.equipos.CreateEquipoScreen
import com.pacheco.gestionfutbol.ui.equipos.EditEquipoScreen
import com.pacheco.gestionfutbol.ui.equipos.EquiposScreen
import com.pacheco.gestionfutbol.ui.theme.GestionFutbolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestionFutbolTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    var currentScreen by remember { mutableStateOf("login") }
                    var equipoSeleccionado by remember { mutableStateOf<Equipo?>(null) }

                    when (currentScreen) {
                        "login" -> LoginScreen(
                            onLoginSuccess = { currentScreen = "home" },
                            onNavigateToRegister = { currentScreen = "register" }
                        )
                        "register" -> RegisterScreen(
                            onRegisterSuccess = { currentScreen = "login" },
                            onNavigateToLogin = { currentScreen = "login" }
                        )
                        "home" -> EquiposScreen(
                            onLogout = { currentScreen = "login" },
                            onNavigateToCreate = { currentScreen = "crear" },
                            onNavigateToEdit = { equipo ->
                                equipoSeleccionado = equipo
                                currentScreen = "editar"
                            }
                        )
                        "crear" -> CreateEquipoScreen(
                            onNavigateBack = { currentScreen = "home" }
                        )
                        "editar" -> {
                            equipoSeleccionado?.let { equipo ->
                                EditEquipoScreen(
                                    equipoActual = equipo,
                                    onNavigateBack = {
                                        equipoSeleccionado = null
                                        currentScreen = "home"
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}