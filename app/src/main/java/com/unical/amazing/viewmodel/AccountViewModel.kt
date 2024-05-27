package com.unical.amazing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unical.amazing.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        // Simula una chiamata di rete per ottenere i dati dell'utente
        viewModelScope.launch {
            val user = User(
                id = "12345",
                name = "John ",
                surname = "Doe",
                phone = 123456789,
                CAP = 88100,
                city = "Catanzaro",
                country = "Italia",
                email = "johndoe@example.com",
                address = "via del tutto eccezionale",
                wishlist = listOf("Item 1", "Item 2", "Item 3"),
                profilePictureUrl = "",
                orders = listOf()
            )
            _user.value = user
        }
    }

    fun updateUserProfilePicture(newProfilePictureUri: String) {
        val currentUser = _user.value ?: return
        val updatedUser = currentUser.copy(profilePictureUrl = newProfilePictureUri)
        _user.value = updatedUser
    }
}
