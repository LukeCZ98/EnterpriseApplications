package com.unical.amazing.viewmodel.account

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.unical.amazing.swagger.models.ProductDto
import com.unical.amazing.swagger.models.UserWDto
import com.unical.amazing.swagger.models.WishlistDto
import com.unical.amazing.swagger.models.WishlistUpdateDTO
import com.unical.amazing.viewmodel.auth.AuthViewModel
import io.swagger.client.apis.UserApi
import io.swagger.client.apis.WishlistApi
import io.swagger.client.models.UserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray

class WishlistViewModel(context: Context) : ViewModel() {

    /*
    * VALORI PER MAPPARE LE WISHLISTS PERSONALI
    * */
    private val _wishlists = MutableStateFlow<List<WishlistDto>?>(null)
    val wishlists: StateFlow<List<WishlistDto>?> get() = _wishlists

    /*
     * VALORI PER MAPPARE LE WISHLISTS CONDIVISE CON L'UTENTE
     * */
    private val _sharedwishlists = MutableStateFlow<List<WishlistDto>?>(null)
    val sharedwishlists: StateFlow<List<WishlistDto>?> get() = _sharedwishlists

    /*
     * VALORI PER MAPPARE LE WISHLISTS PUBBLICHE ACCESSIBILI DA TUTTI
     * */
    private val _publicwishlists = MutableStateFlow<List<WishlistDto>?>(null)
    val publicwishlists: StateFlow<List<WishlistDto>?> get() = _publicwishlists


    private val token = AuthViewModel(context).getToken()
    private val wish = WishlistApi(context)


    init {
        fetchUsrWishlists()
        fetchUsrsharedlists()
        publicWishlists()
    }

    /*
    * METODI DI FETCHING PER ESTRARRE LE LISTE DAL DB
    *
    *
    * */
    private fun fetchUsrWishlists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = token?.let { wish.getAllByUser(it) }
                if (response != null) {
                    val jsonResponse = response as List<Map<String, Any>>
                    val responseString = JSONArray(jsonResponse).toString()
                    val wishlistDtos = parseWishlists(responseString)
                    _wishlists.value = wishlistDtos
                } else {
                    println("Response is null for fetchUsrWishlists")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _wishlists.value = emptyList()
            }
        }
    }

    private fun fetchUsrsharedlists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = token?.let { wish.getsharedwithUser(it) }
                if (response != null) {
                    val jsonResponse = response as List<Map<String, Any>>
                    val responseString = JSONArray(jsonResponse).toString()
                    println("Converted JSON Response: $responseString")
                    val wishlistDtos = parseWishlists(responseString)
                    _sharedwishlists.value = wishlistDtos
                } else {
                    println("Response is null for fetchUsrsharedlists")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _sharedwishlists.value = emptyList()
            }
        }
    }

    private fun parseWishlists(response: String): List<WishlistDto> {
        val jsonArray = JSONArray(response)
        val wishlists = mutableListOf<WishlistDto>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val wishlistObject = if (jsonObject.has("wishlist")) jsonObject.getJSONObject("wishlist") else jsonObject
            val itemsJsonArray = wishlistObject.getJSONArray("items")
            val items = mutableListOf<ProductDto>()

            for (j in 0 until itemsJsonArray.length()) {
                val itemJsonObject = itemsJsonArray.getJSONObject(j)
                val product = ProductDto(
                    id = itemJsonObject.getLong("id"),
                    title = itemJsonObject.getString("title"),
                    description = itemJsonObject.getString("description"),
                    img_url = itemJsonObject.getString("img_url"),
                    price = itemJsonObject.getDouble("price"),
                    available = itemJsonObject.getBoolean("available")
                )
                items.add(product)
            }

            val wishlist = WishlistDto(
                id = wishlistObject.getLong("id"),
                name = wishlistObject.getString("name"),
                visibility = wishlistObject.getString("visibility"),
                items = items
            )
            wishlists.add(wishlist)
        }

        return wishlists
    }

    private fun publicWishlists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = token?.let { wish.getpublic(it) }
                if (response != null) {
                    val jsonResponse = response as List<Map<String, Any>>
                    val responseString = JSONArray(jsonResponse).toString()
                    val wishlistDtos = parseWishlists(responseString)
                    _publicwishlists.value = wishlistDtos
                } else {
                    println("Response is null for fetchpublicWishlists")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _publicwishlists.value = emptyList()
            }
        }
    }


    /*
    * METODI PER MODIFICARE LE LISTE PERSONALI
    * */

    fun createwishlist(wishlist: WishlistDto){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = token?.let { wish.create(it,wishlist) }

            }
            catch (e : Exception){
                println(e.message)
            }
        }
    }
     fun deletewishlist(id: Long){
         viewModelScope.launch(Dispatchers.IO) {
             try {
                 val response = token?.let { wish.delete(it,id) }
             }
             catch (e : Exception){
                 println(e.message)
             }
         }
    }

    fun updatewishlist(wishlist: WishlistDto, users: List<String>?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Creare l'oggetto aggiornato
                val updateRequest = WishlistUpdateDTO(wishlist, users ?: emptyList())

                // Serializzare l'oggetto in JSON per verificarne la struttura
                val gson = Gson()
                val jsonString = gson.toJson(updateRequest)

                println("JSON da inviare: $jsonString") // Log del JSON per il debug

                // Inviare la richiesta al server
                val response = token?.let { wish.update(it, updateRequest) }

                println("response: $response")
            } catch (e: Exception) {
                println("Errore durante l'aggiornamento della wishlist")
                e.printStackTrace()
            }
        }
    }



    fun addProductToWishlist(productId: Long, wishlistId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = token?.let { wish.addProductToWishlist(it, productId, wishlistId) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun removeProductFromWishlist(productId: Long, wishlistId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response = token?.let { wish.removeProductFromWishlist(it,productId,wishlistId) }
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}
