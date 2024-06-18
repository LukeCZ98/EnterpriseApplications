package com.unical.amazing.swagger.models

data class AddressDto (
    val id: Int,
    val address: String,
    val city: String,
    val country: String,
    val phone: String,
    val cap: Int
)