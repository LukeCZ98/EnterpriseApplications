package com.unical.amazing.swagger.models

data class ProductWithQuantity(
    val product: ProductDto,
    var quantity: Int
)