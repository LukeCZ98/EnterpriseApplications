package com.unical.amazing.view.admin

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.unical.amazing.swagger.models.UserDto
import com.unical.amazing.viewmodel.admin.users.UserViewModel
import com.unical.amazing.viewmodel.admin.users.SharedUserViewModel
import com.unical.amazing.viewmodel.factories.UserManagementViewModelFactory

@SuppressLint("RememberReturnType")
@Composable
fun UserManagementView(context: Context, adminNavController: NavHostController, sharedUserViewModel: SharedUserViewModel) {
    val viewModelFactory = remember { UserManagementViewModelFactory(context) }
    val viewModel: UserViewModel = viewModel(factory = viewModelFactory)
    val users by viewModel.users.collectAsState()
    var userList by remember { mutableStateOf(users ?: emptyList<UserDto>()) }


    // Update userList when users changes
    LaunchedEffect(users) {
        userList = users ?: emptyList()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(users ?: emptyList()) { user ->
                UserItem(
                    user = user,
                    viewModel = viewModel,
                    sharedUserViewModel = sharedUserViewModel,
                    adminNavController = adminNavController
                )
            }
        }
    }
}

@Composable
fun UserItem(
    user: UserDto,
    viewModel: UserViewModel,
    sharedUserViewModel: SharedUserViewModel,
    adminNavController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            user.username.let { Text(text = it) }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    sharedUserViewModel.selectedUser = user
                    adminNavController.navigate("edituserorders")
                }
            ) {
                Text("Ordini")
            }

            Button(
                onClick = {
                    viewModel.deleteUser(user.id)
                }
            ) {
                Text("Elimina")
            }
        }
    }
}
