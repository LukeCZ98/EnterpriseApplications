package com.unical.amazing.viewmodel.admin.users

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.unical.amazing.swagger.apis.UserApi
import com.unical.amazing.swagger.models.OrderDto
import com.unical.amazing.swagger.models.ProductDto
import com.unical.amazing.swagger.models.ProductWithQuantity
import com.unical.amazing.swagger.models.ProductWithQuantityDto
import com.unical.amazing.swagger.models.UserDto
import com.unical.amazing.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(context: Context) : ViewModel() {
    private val _users = MutableStateFlow<List<UserDto>?>(null)
    val users: StateFlow<List<UserDto>?> get() = _users

    private val _userorders = MutableStateFlow<List<OrderDto>?>(null)
    val userorders: StateFlow<List<OrderDto>?> get() = _userorders
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val productDtoAdapter: JsonAdapter<ProductDto> = moshi.adapter(ProductDto::class.java)

    private val token = AuthViewModel(context).getToken()
    private val usr = UserApi(context)


    init{
        fetchAll()
    }

    private fun fetchAll() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                _users.value = token?.let { usr.getAll(it) }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
    }


    fun fetchUserOrders(usrid:String){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response = token?.let { usr.getUserOrders(it,usrid.toDouble().toLong()) }
                val ordersMap = mutableMapOf<Long, MutableList<ProductWithQuantity>>()

                response?.forEach { outerMap ->
                    val orderId = (outerMap["id"] as? Number)?.toLong() ?: return@forEach
                    val quantities = outerMap["quantities"] as? List<Map<String, Any?>>

                    quantities?.forEach { orderMap ->
                        val productMap = orderMap["product"] as? Map<String, Any?>
                        val productDto = productMap?.let {
                            val productJson = moshi.adapter(Map::class.java).toJson(it)
                            productDtoAdapter.fromJson(productJson)
                        }
                        val quantity = (orderMap["quantity"] as? Number)?.toInt()

                        if (productDto != null && quantity != null) {
                            ordersMap.getOrPut(orderId) { mutableListOf() }.add(ProductWithQuantity(productDto, quantity))
                        }
                    }
                }

                // Convertire la mappa in una lista di OrderDto
                val orderDtos = ordersMap.map { (orderId, productsList) ->
                    OrderDto(
                        id = orderId,
                        products = productsList.map { ProductWithQuantityDto(it.product, it.quantity) }
                    )
                }
                _userorders.value = orderDtos
            }
            catch(e:Exception){
                e.printStackTrace()
                _userorders.value = emptyList()
            }
        }
    }


    fun deleteUser(userid: String) {
        viewModelScope.launch(Dispatchers.IO)  {
            try{
                token?.let { usr.delUser(it,userid.toDouble().toLong()) }
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }


    fun deleteUserOrder(ordid: Long) {
        viewModelScope.launch(Dispatchers.IO)  {
            try{
                token?.let { usr.delUserOrd(it,ordid) }
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }
}
