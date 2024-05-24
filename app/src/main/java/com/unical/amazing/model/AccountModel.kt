package com.unical.amazing.model

// User.kt
data class User(
    val id: String,
    val name: String,
    val email: String,
    val profilePictureUrl: String,
    val orders: List<Order>
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
