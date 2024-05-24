package com.unical.amazing.viewmodel

// AccountViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unical.amazing.model.Order
import com.unical.amazing.model.OrderItem
import com.unical.amazing.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    private val _user = MutableStateFlow(User(
        id = "12345",
        name = "John Doe",
        email = "johndoe@example.com",
        profilePictureUrl = "https://example.com/profile.jpg",
        orders = listOf(
            Order(
                orderId = "order123",
                date = "2023-01-01",
                status = "Delivered",
                items = listOf(
                    OrderItem("item123", "Product 1", 1, 29.99),
                    OrderItem("item124", "Product 2", 2, 49.99)
                )
            )
        )
    ))
    val user: StateFlow<User> get() = _user

    fun fetchUserData() {
        // Simula una chiamata di rete o una lettura da un database
        viewModelScope.launch {
            // Aggiorna i dati dell'utente
        }
    }
}
