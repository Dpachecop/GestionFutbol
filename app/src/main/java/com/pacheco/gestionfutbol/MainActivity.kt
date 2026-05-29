package com.pacheco.gestionfutbol

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.pacheco.gestionfutbol.ui.auth.*
import com.pacheco.gestionfutbol.ui.equipos.*
import com.pacheco.gestionfutbol.ui.theme.GestionFutbolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestionFutbolTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    var currentScreen by remember { mutableStateOf("login") }

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
                            onNavigateToCreate = { currentScreen = "crear" }
                        )
                        "crear" -> CreateEquipoScreen(
                            onNavigateBack = { currentScreen = "home" }
                        )
                    }
                }
            }
        }
    }
}