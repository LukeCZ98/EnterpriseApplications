package com.unical.amazing.viewmodel.admin.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.unical.amazing.swagger.models.UserDto

class SharedUserViewModel : ViewModel() {
    var selectedUser: UserDto? by mutableStateOf(null)
}