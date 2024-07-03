package com.unical.amazing.swagger.models

import io.swagger.client.models.UserDto

data class WishlistUpdateDTO (
     var wishlist: WishlistDto,
     var users: List<String>? = emptyList()
)
