package com.unical.amazing.model.account

data class WishlistModel(
    val id: Int,
    val name: String,
    var isPublic: Boolean,
    val items: List<Item>
)
