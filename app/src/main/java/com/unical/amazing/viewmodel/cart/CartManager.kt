package com.unical.amazing.viewmodel.cart

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.unical.amazing.swagger.models.ProductDto
import com.unical.amazing.swagger.models.ProductWithQuantity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "cart")

class CartManager(private val context: Context) {

    private val cartItemsKey = stringPreferencesKey("cart_items")
    private val gson = Gson()

    suspend fun addItemToCart(cartItem: ProductWithQuantity) {
        context.dataStore.edit { preferences ->
            val currentCart = preferences[cartItemsKey]?.let { deserializeCart(it) } ?: mutableListOf()
            val existingItem = currentCart.find { it.product.id == cartItem.product.id }
            if (existingItem != null) {
                existingItem.quantity += cartItem.quantity
            } else {
                currentCart.add(cartItem)
            }
            preferences[cartItemsKey] = serializeCart(currentCart)
        }
    }

    suspend fun removeItemFromCart(cartItem: ProductWithQuantity) {
        context.dataStore.edit { preferences ->
            val currentCart = preferences[cartItemsKey]?.let { deserializeCart(it) } ?: mutableListOf()
            currentCart.removeAll { it.product.id == cartItem.product.id }
            preferences[cartItemsKey] = serializeCart(currentCart)
        }
    }

    suspend fun updateItemQuantity(cartItem: ProductWithQuantity, newQuantity: Int) {
        context.dataStore.edit { preferences ->
            val currentCart = preferences[cartItemsKey]?.let { deserializeCart(it) } ?: mutableListOf()
            val itemIndex = currentCart.indexOfFirst { it.product.id == cartItem.product.id }
            if (itemIndex != -1) {
                currentCart[itemIndex] = currentCart[itemIndex].copy(quantity = newQuantity)
                preferences[cartItemsKey] = serializeCart(currentCart)
            }
        }
    }

    fun getCartItems(): Flow<List<ProductWithQuantity>> {
        return context.dataStore.data
            .map { preferences ->
                preferences[cartItemsKey]?.let { deserializeCart(it) } ?: emptyList()
            }
    }

    private fun serializeCart(cart: List<ProductWithQuantity>): String {
        return gson.toJson(cart)
    }

    private fun deserializeCart(serializedCart: String): MutableList<ProductWithQuantity> {
        if (serializedCart.isEmpty()) {
            return mutableListOf()
        }
        val type = object : TypeToken<MutableList<ProductWithQuantity>>() {}.type
        return gson.fromJson(serializedCart, type) ?: mutableListOf()
    }

    fun getCartItemCount(): Flow<Int> {
        return context.dataStore.data
            .map { preferences ->
                preferences[cartItemsKey]?.let { deserializeCart(it) }?.sumOf { it.quantity } ?: 0
            }
    }

    suspend fun clearCart() {
        context.dataStore.edit { preferences ->
            preferences.remove(cartItemsKey)
        }
    }
}
