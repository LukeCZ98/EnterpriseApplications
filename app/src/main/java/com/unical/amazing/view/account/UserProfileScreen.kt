package com.unical.amazing.view.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.unical.amazing.model.User
import com.unical.amazing.viewmodel.AccountViewModel

@Composable
fun UserProfileScreen(accountViewModel: AccountViewModel = viewModel()) {
    val userState = accountViewModel.user.collectAsState()

    userState.value?.let { user ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            UserProfileHeader(user) { uri ->
                // Handle the new profile image URI, e.g., update the user profile picture
                accountViewModel.updateUserProfilePicture(uri.toString())
            }
            Spacer(modifier = Modifier.height(16.dp))
            UserProfileDetails(user)
            Spacer(modifier = Modifier.height(16.dp))
            EditProfileButton()
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
fun UserProfileHeader(user: User, onProfileImageChange: (Uri) -> Unit) {
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
                    painter = rememberImagePainter(profileImageUri ?: user.profilePictureUrl),
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
                Text(text = user.name, style = MaterialTheme.typography.h5)
                Text(text = user.email, style = MaterialTheme.typography.body1, color = Color.Gray)
            }
        }
    }
}

@Composable
fun UserProfileDetails(user: User) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserProfileDetailItem(label = "name", value = user.name)
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(label = "surname", value = user.surname)
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(label = "email", value = user.email)
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(label = "Address", value = user.address)
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(label = "Phone", value = user.phone.toString())
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(label = "CAP", value = user.CAP.toString())
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(label = "city", value = user.city)
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

            UserProfileDetailItem(label = "country", value = user.country)
            Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))

        }
    }
}

@Composable
fun UserProfileDetailItem(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.caption, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.body1, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun EditProfileButton() {
    Button(
        onClick = { /* Implement edit functionality */ },
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text("Edit Profile", fontSize = 16.sp)
    }
}
