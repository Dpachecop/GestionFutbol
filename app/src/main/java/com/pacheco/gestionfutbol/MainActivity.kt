package com.pacheco.gestionfutbol

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.pacheco.gestionfutbol.ui.auth.LoginScreen
import com.pacheco.gestionfutbol.ui.theme.GestionFutbolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplica el tema de Material 3 generado por Android Studio
            GestionFutbolTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Invocamos la pantalla de Login que creamos
                    LoginScreen(
                        onLoginSuccess = {
                            // Este bloque se ejecuta cuando el AuthState pasa a Success
                            Toast.makeText(this, "¡Login Exitoso en MockAPI!", Toast.LENGTH_LONG).show()
                        },
                        onNavigateToRegister = {
                            // Este bloque se ejecuta al presionar el botón de ir a registro
                            Toast.makeText(this, "Abriendo pantalla de registro...", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}