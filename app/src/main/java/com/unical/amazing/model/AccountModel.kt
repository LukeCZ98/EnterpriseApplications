package com.unical.amazing.model

import com.unical.amazing.model.account.WishlistModel


data class User(
    val id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val address: String,
    val phone: String,
    val CAP: Int,
    val city: String,
    val country: String,
    val picurl: String,
    val orders: List<Order>,
    val wishlistModels: List<WishlistModel>
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

data class Item(
    val itemId: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val image: String,
    val isPublic: Boolean
)
