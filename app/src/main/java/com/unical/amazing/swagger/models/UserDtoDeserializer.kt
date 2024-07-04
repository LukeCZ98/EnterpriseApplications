package com.unical.amazing.swagger.models

import com.google.gson.*
import java.lang.reflect.Type

class UserDtoDeserializer : JsonDeserializer<UserDto> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): UserDto {
        val jsonObject = json.asJsonObject

        val addressesJsonArray = jsonObject.getAsJsonArray("addresses") ?: JsonArray()
        val address = if (addressesJsonArray.size() > 0) {
            addressesJsonArray[0].asJsonObject
        } else {
            JsonObject()
        }

        return UserDto(
            id = jsonObject.get("id").asLong.toString(),
            username = jsonObject.get("username").asString,
            firstName = jsonObject.get("firstName").asString,
            lastName = jsonObject.get("lastName").asString,
            email = jsonObject.get("email").asString,
            addresses = addressesJsonArray.map { context.deserialize(it, AddressDto::class.java) },
            phone = address.get("phone")?.asString ?: "",
            CAP = address.get("cap")?.asInt ?: 0,
            city = address.get("city")?.asString ?: "",
            country = address.get("country")?.asString ?: "",
            orders = emptyList(), // Modifica questo se hai bisogno di deserializzare gli ordini
            wishlistModels = emptyList() // Modifica questo se hai bisogno di deserializzare le wishlistModels
        )
    }
}

