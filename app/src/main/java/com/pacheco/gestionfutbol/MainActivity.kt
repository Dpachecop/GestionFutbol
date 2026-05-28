package com.pacheco.gestionfutbol

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.pacheco.gestionfutbol.ui.auth.LoginScreen
import com.pacheco.gestionfutbol.ui.auth.RegisterScreen
import com.pacheco.gestionfutbol.ui.theme.GestionFutbolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestionFutbolTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Variable de estado para controlar la navegación
                    var currentScreen by remember { mutableStateOf("login") }

                    when (currentScreen) {
                        "login" -> {
                            LoginScreen(
                                onLoginSuccess = {
                                    Toast.makeText(this, "¡Bienvenido a la app!", Toast.LENGTH_LONG).show()
                                    // Aquí luego navegaremos a la lista de equipos
                                },
                                onNavigateToRegister = {
                                    currentScreen = "register" // Cambia la pantalla a Registro
                                }
                            )
                        }
                        "register" -> {
                            RegisterScreen(
                                onRegisterSuccess = {
                                    Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_LONG).show()
                                    currentScreen = "login" // Devuelve al login tras crear cuenta
                                },
                                onNavigateToLogin = {
                                    currentScreen = "login" // Vuelve al login si se arrepiente
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}