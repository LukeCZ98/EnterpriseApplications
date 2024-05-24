package com.unical.amazing.view.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.compose.foundation.layout.size
import com.unical.amazing.model.User

@Composable
fun UserProfileSection(user: User) {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = "User Profile",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberImagePainter(user.profilePictureUrl),
                contentDescription = "User Profile Picture",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    text = "Name: ${user.name}",
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp
                )
                Text(
                    text = "Email: ${user.email}",
                    style = MaterialTheme.typography.body2,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
