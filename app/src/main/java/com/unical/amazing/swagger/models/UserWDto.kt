package com.unical.amazing.swagger.models

data class UserWDto(
    val id: Long?=null,
    val username: String,
    val firstName: String?=null,
    val lastName: String?=null,
    val email: String?=null,
)