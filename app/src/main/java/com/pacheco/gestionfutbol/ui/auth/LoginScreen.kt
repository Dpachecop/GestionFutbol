package com.pacheco.gestionfutbol.ui.auth
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    // Inyectamos el ViewModel por defecto
    viewModel: AuthViewModel = viewModel(),
    // Funciones "callback" para navegar (como el Navigator.push de Flutter)
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    // 1. Observamos el estado del ViewModel de forma reactiva
    val authState by viewModel.authState.collectAsState()

    // 2. Estados locales para los campos de texto (tu equivalente a los TextEditingController)
    var correo by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }

    // 3. Efecto secundario: Si el login es exitoso, navegamos a la siguiente pantalla
    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onLoginSuccess()
            viewModel.resetState() // Reseteamos el estado para no navegar doble
        }
    }

    // 4. Construcción de la UI (Tu "build" method)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de Correo
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Contraseña
        OutlinedTextField(
            value = contrasenia,
            onValueChange = { contrasenia = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 5. Renderizado condicional basado en el estado
        when (authState) {
            is AuthState.Loading -> {
                CircularProgressIndicator() // Ruedita de carga
            }
            else -> {
                Button(
                    onClick = { viewModel.login(correo, contrasenia) },
                    modifier = Modifier.fillMaxWidth(),
                    // El botón se desactiva si los campos están vacíos
                    enabled = correo.isNotBlank() && contrasenia.isNotBlank()
                ) {
                    Text("Ingresar")
                }
            }
        }

        // Si hay error, mostramos el mensaje en rojo
        if (authState is AuthState.Error) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = (authState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para ir al registro
        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate aquí")
        }
    }
}