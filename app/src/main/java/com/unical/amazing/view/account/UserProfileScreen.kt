package com.unical.amazing.view.account

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.unical.amazing.viewmodel.UserProfileViewModelFactory
import com.unical.amazing.viewmodel.account.UserProfileViewModel
import io.swagger.client.models.UserDto

@Composable
fun UserProfileScreen(context: Context) {
    val viewModelFactory = remember { UserProfileViewModelFactory(context) }
    val userprofileviewModel: UserProfileViewModel = viewModel(factory = viewModelFactory)
    val userState = userprofileviewModel.user.collectAsState()

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
                    UserProfileHeader(user) { uri ->
                        userprofileviewModel.updateUserProfilePicture(uri.toString())
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    UserProfileDetails(user) { updatedUser ->
                        userprofileviewModel.updateUser(updatedUser)
                    }
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
fun UserProfileHeader(user: UserDto, onProfileImageChange: (Uri) -> Unit) {
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                profileImageUri = it
                onProfileImageChange(it)
            }
        }
    )

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(80.dp)) {
                Image(
                    painter = rememberImagePainter(profileImageUri ?: user.picurl),
                    contentDescription = "User Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.onSurface.copy(alpha = 0.2f)),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .background(MaterialTheme.colors.primary, CircleShape)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Profile Image", tint = Color.White)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = user.username, style = MaterialTheme.typography.h5)
                Text(text = user.email, style = MaterialTheme.typography.body1, color = Color.Gray)
            }
        }
    }
}

@Composable
fun UserProfileDetails(user: UserDto, onSave: (UserDto) -> Unit) {
    var isEditing by remember { mutableStateOf(false) }
    var editableUser by remember { mutableStateOf(user) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserProfileDetailItem(
                label = "Name",
                value = editableUser.firstName,
                isEditing = false, // Name is not editable
                onValueChange = { editableUser = editableUser.copy(firstName = it) }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "Surname",
                value = editableUser.lastName,
                isEditing = false, // Surname is not editable
                onValueChange = { editableUser = editableUser.copy(lastName = it) }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "Username",
                value = editableUser.username,
                isEditing = false, // Surname is not editable
                onValueChange = { editableUser = editableUser.copy(username = it) }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "Email",
                value = editableUser.email,
                isEditing = false, // Email is not editable
                onValueChange = { editableUser = editableUser.copy(email = it) }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "Address",
                value = editableUser.addresses.get(0).address,
                isEditing = isEditing,
                onValueChange = { newAddress ->
                    // Copia l'indirizzo corrente
                    val updatedAddress = editableUser.addresses[0].copy(address = newAddress)

                    // Aggiorna la lista degli indirizzi con l'indirizzo modificato
                    val updatedAddresses = editableUser.addresses.toMutableList().apply {
                        this[0] = updatedAddress
                    }

                    // Aggiorna editableUser con la nuova lista di indirizzi
                    editableUser = editableUser.copy(addresses = updatedAddresses)
                }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "Phone",
                value = editableUser.phone,
                isEditing = isEditing,
                onValueChange = { editableUser = editableUser.copy(phone = it) }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "CAP",
                value = editableUser.CAP.toString(),
                isEditing = isEditing,
                onValueChange = { editableUser = editableUser.copy(CAP = it.toInt()) }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "City",
                value = editableUser.city,
                isEditing = isEditing,
                onValueChange = { editableUser = editableUser.copy(city = it) }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(
                label = "Country",
                value = editableUser.country,
                isEditing = isEditing,
                onValueChange = { editableUser = editableUser.copy(country = it) }
            )
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isEditing) {
                        onSave(editableUser)
                    }
                    isEditing = !isEditing
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(if (isEditing) "Save" else "Edit")
            }
        }
    }
}

@Composable
fun UserProfileDetailItem(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.caption, color = Color.Gray)
        if (isEditing && label !in listOf("Name", "Surname", "Email")) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(text = value, style = MaterialTheme.typography.body1, modifier = Modifier.padding(top = 4.dp))
        }
    }
}


