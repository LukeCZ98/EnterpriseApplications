package com.unical.amazing.model

data class Wishlist(
    val id: Int,
    val name: String,
    var isPublic: Boolean,
    val items: List<Item>
)
