package com.unical.amazing.model.account

import com.unical.amazing.model.Item

data class WishlistModel(
    val id: Int,
    val name: String,
    var isPublic: Boolean,
    val items: List<Item>
)
