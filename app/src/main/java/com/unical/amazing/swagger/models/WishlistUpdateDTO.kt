package com.unical.amazing.swagger.models

data class WishlistUpdateDTO (
     var wishlist: WishlistDto,
     var users: List<String>? = emptyList()
)
