package com.unical.amazing.view.auth

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavController
import com.unical.amazing.R
import com.unical.amazing.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    navController: NavController,
    isLoggedIn: MutableState<Boolean>,
    onRegister: (String, String, String, String, String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isRegistering by remember { mutableStateOf(false) } // Toggle tra login e registrazione
    var errorMessage by remember { mutableStateOf("") }
    val context: Context = LocalContext.current
    val auth = remember { AuthViewModel(context) }
    val coroutineScope = rememberCoroutineScope() // Scope per avviare coroutine


    val sharedPreferences = remember {
        context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    }

    // Caricamento dello stato della checkbox "Ricordami" dalle SharedPreferences
    var rememberMeState by remember {
        mutableStateOf(sharedPreferences.getBoolean("remember_me", false))
    }

    // Salva il valore della checkbox "Ricordami" nelle SharedPreferences quando cambia
    DisposableEffect(Unit) {
        onDispose {
            sharedPreferences.edit {
                putBoolean("remember_me", rememberMeState)
                apply()
            }
        }
    }

    // Quando "Ricordami" è selezionato, salva i dati di login nelle SharedPreferences
    if (rememberMeState) {
        DisposableEffect(Unit) {
            onDispose {
                sharedPreferences.edit {
                    putString("username", username)
                    putString("password", password)
                    apply()
                }
            }
        }
    }


    // Gestione click login
    val handleLoginClick: () -> Unit = {
        coroutineScope.launch {
            val loginData = mapOf("username" to username, "password" to password)
            val response = auth.login(loginData)
            if (response?.get("success") == true) {
                isLoggedIn.value = true
            } else {
                val error = response?.get("message")?.toString() ?: "Autenticazione fallita"
                errorMessage = error
            }
        }
    }


    /*
    * (?=.*[A-Z]): Assicura che ci sia almeno una lettera maiuscola.
      (?=.*[a-z]): Assicura che ci sia almeno una lettera minuscola.
      (?=.*\\d): Assicura che ci sia almeno un numero.
      (?=.*[!@#$%^&*]): Assicura che ci sia almeno un carattere speciale tra quelli specificati.
      [A-Za-z\\d!@#$%^&*]{6,32}: Definisce il set di caratteri ammessi e impone che la lunghezza sia tra 6 e 32 caratteri.
    * */



    // Gestione click registrazione
    val handleRegisterClick: () -> Unit = {
        val passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\\\d)(?=.*[!@#\$%^&*])[A-Za-z\\\\d!@#\$%^&*]{8,32}\$".toRegex()
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

        if (password == confirmPassword && username.isNotBlank() && password.isNotBlank() &&
            email.isNotBlank() && firstName.isNotBlank() && lastName.isNotBlank()) {

            if (password.matches(passwordRegex)) {
                if (email.matches(emailRegex))  {
                    coroutineScope.launch {
                        val regData = mapOf("username" to username,"email" to email, "password" to password,"firstName" to firstName,"lastName" to lastName)
                        val response = auth.register(regData)
                        if(response == "200"){
                            errorMessage = "Registrazione avvenuta correttamente,\n ora verificare la mail per attivare il tuo account."
                            onRegister(username, password, email, firstName, lastName)
                            navController.navigate("login") { popUpTo("login") { inclusive = true } }
                        }
                    }


                }
                else {
                    errorMessage = "L'email non è valida."
                }
            }
            else{
                errorMessage = "La password deve contenere almeno 8 caratteri, inclusi almeno una lettera maiuscola, una lettera minuscola, un numero e un carattere speciale."
            }

        } else {
            errorMessage = "Compila tutti i campi e assicurati che le password corrispondano."
        }
    }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = rememberScaffoldState(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState()) // Abilita la scorrevolezza verticale
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = if (isRegistering) "Registrati" else "Accedi",
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
                )
                if (isRegistering) {
                    AuthTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        leadingIcon = Icons.Default.Email
                    )
                    AuthTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = "Nome",
                        leadingIcon = Icons.Default.Person
                    )
                    AuthTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = "Cognome",
                        leadingIcon = Icons.Default.Person
                    )
                }
                AuthTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = "Username",
                    leadingIcon = Icons.Default.Person
                )
                AuthTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true
                )
                if (isRegistering) {
                    AuthTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = "Conferma Password",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (!isRegistering) {
                    Checkbox(
                        checked = rememberMeState,
                        onCheckedChange = {
                            rememberMeState = it
                            if (!it) {
                                // Se "Ricordami" viene deselezionato, rimuovi i dati salvati
                                sharedPreferences.edit {
                                    remove("username")
                                    remove("password")
                                    apply()
                                }
                            }
                        }
                    )
                        Text(text = "Ricordami")
                    }
                }
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = MaterialTheme.colors.error)
                }
                Button(
                    onClick = if (isRegistering) handleRegisterClick else handleLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(text = if (isRegistering) "Registrati" else "Accedi", fontSize = 18.sp)
                }
                TextButton(onClick = { isRegistering = !isRegistering }) {
                    Text(text = if (isRegistering) "Hai già un account? Accedi" else "Non hai un account? Registrati")
                }
            }
        }
    }
}

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(50),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}
