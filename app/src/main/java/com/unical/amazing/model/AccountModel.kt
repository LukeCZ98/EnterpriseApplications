package com.unical.amazing.model

// User.kt
data class User(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val address: String,
    val phone: Int,
    val CAP: Int,
    val city: String,
    val country: String,
    val profilePictureUrl: String,
    val orders: List<Order>,
    val wishlist:List<String>
)

data class Order(
    val orderId: String,
    val date: String,
    val status: String,
    val items: List<OrderItem>
)

data class OrderItem(
    val itemId: String,
    val name: String,
    val quantity: Int,
    val price: Double
)
