package com.unical.amazing.swagger.models


data class WishlistDto(
    val id: Long,
    var name: String,
    var visibility: String,
    val items: List<ProductDto>? = null,
    val sharedwith: List<UserWDto>? = null
)
