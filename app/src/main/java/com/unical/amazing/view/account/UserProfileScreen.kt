package com.unical.amazing.view.account

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.viewmodel.account.UserProfileViewModelFactory
import com.unical.amazing.viewmodel.account.UserProfileViewModel
import io.swagger.client.models.UserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UserProfileScreen(context: Context) {
    val viewModelFactory = remember { UserProfileViewModelFactory(context) }
    val usprvwmd: UserProfileViewModel = viewModel(factory = viewModelFactory)
    val userState = usprvwmd.user.collectAsState()

    userState.value?.let { user ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("I miei dati") }
                )
            },
            bottomBar = {
                BottomAppBar {
                    // Optionally, add items to the bottom bar
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                item {
                    UserProfileHeader(user)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    UserProfileDetails(user)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}





@Composable
fun UserProfileHeader(user: UserDto) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = user.username, style = MaterialTheme.typography.h5)
                Text(text = user.email, style = MaterialTheme.typography.body1, color = Color.Gray)
            }
        }
    }
}



@Composable
fun UserProfileDetails(user: UserDto) {
    var isEditing by remember { mutableStateOf(false) }
    var editableUser by remember { mutableStateOf(user) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val viewModelFactory = remember { UserProfileViewModelFactory(context) }
    val usr: UserProfileViewModel = viewModel(factory = viewModelFactory)
    var errorMessage by remember { mutableStateOf("") }

    val handleSaveClick: () -> Unit = {
        if (isEditing) {
            if (isUserValid(editableUser)) {
                coroutineScope.launch(Dispatchers.IO) {
                    val response = usr.updateUser(editableUser)
//                    println("Response di updateuser: $response")
                    if(response == "200"){
                        errorMessage = "Profilo modificato correttamente."
                    }
                    else{
                        errorMessage = "Errore durante la modifica del profilo."
                    }
                }
            } else {
                errorMessage = "I dati inseriti non sono validi,ricontrollare i campi."
            }
        }
        isEditing = !isEditing
    }



    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserProfileDetailItem(
                label = "Nome",
                value = editableUser.firstName,
                isEditing = false, // Nome non modificabile
                onValueChange = { editableUser = editableUser.copy(firstName = it) }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "Cognome",
                value = editableUser.lastName,
                isEditing = false, // Cognome non modificabile
                onValueChange = { editableUser = editableUser.copy(lastName = it) }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "Nickname",
                value = editableUser.username,
                isEditing = false, // Nickname non modificabile
                onValueChange = { editableUser = editableUser.copy(username = it) }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "Email",
                value = editableUser.email,
                isEditing = false, // Email non modificabile
                onValueChange = { editableUser = editableUser.copy(email = it) }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            editableUser.addresses?.get(0)?.let {
                UserProfileDetailItem(
                    label = "Indirizzo",
                    value = it.address,
                    isEditing = isEditing,
                    onValueChange = { newAddress ->
                        val updatedAddress = editableUser.addresses!![0].copy(address = newAddress)
                        val updatedAddresses = editableUser.addresses!!.toMutableList().apply {
                            this[0] = updatedAddress
                        }
                        editableUser = editableUser.copy(addresses = updatedAddresses)
                    },
                    regex = "^[\\w\\s,.#-]{1,500}$", // Regex per validare l'indirizzo
                    errorMessage = "Indirizzo non valido"
                )
            }
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "Telefono",
                value = editableUser.phone,
                isEditing = isEditing,
                onValueChange = { editableUser = editableUser.copy(phone = it) },
                regex = "^[0-9]{10,15}$", // Regex per validare il telefono
                errorMessage = "Il numero di telefono deve essere tra 10 e 15 cifre."
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "CAP",
                value = editableUser.CAP.toString(),
                isEditing = isEditing,
                onValueChange = { editableUser = editableUser.copy(CAP = it.toInt()) },
                regex = "^\\d{5}$", // Regex per validare il CAP
                errorMessage = "Il CAP deve essere di 5 cifre."
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "Cittá",
                value = editableUser.city,
                isEditing = isEditing,
                onValueChange = { editableUser = editableUser.copy(city = it) },
                regex = "^[a-zA-Z\\s]{1,50}$", // Regex per validare la città
                errorMessage = "La città deve contenere solo lettere."
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "Paese",
                value = editableUser.country,
                isEditing = isEditing,
                onValueChange = { editableUser = editableUser.copy(country = it) },
                regex = "^[a-zA-Z\\s]{1,50}$", // Regex per validare il paese
                errorMessage = "Il paese deve contenere solo lettere."
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            Spacer(modifier = Modifier.height(16.dp))
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colors.error)
            }
            Button(
                onClick = handleSaveClick, // Utilizza la funzione separata
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(if (isEditing) "Salva" else "Modifica")
            }
        }
    }
}




@Composable
fun UserProfileDetailItem(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit,
    regex: String? = null,
    errorMessage: String = ""
) {
    var isValid by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.caption, color = Color.Gray)
        if (isEditing) {
            TextField(
                value = value,
                onValueChange = {
                    if (regex != null && !it.matches(regex.toRegex())) {
                        isValid = false
                    } else {
                        isValid = true
                    }
                    onValueChange(it)
                },
                singleLine = true,
                isError = !isValid,
                modifier = Modifier.fillMaxWidth()
            )
            if (!isValid) {
                Text(text = errorMessage, color = Color.Red, style = MaterialTheme.typography.caption)
            }
        } else {
            Text(text = value, style = MaterialTheme.typography.body1, modifier = Modifier.padding(top = 4.dp))
        }
    }
}





fun isUserValid(user: UserDto): Boolean {
    return user.firstName.isNotBlank() && user.lastName.isNotBlank() &&
            user.username.isNotBlank() && user.email.isNotBlank() &&
            user.phone.matches("^[0-9]{10,15}$".toRegex()) &&
            user.CAP.toString().matches("^\\d{5}$".toRegex()) &&
            user.city.matches("^[a-zA-Z\\s]{1,50}$".toRegex()) &&
            user.country.matches("^[a-zA-Z\\s]{1,50}$".toRegex())
}



