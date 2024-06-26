package com.unical.amazing.viewmodel.account

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unical.amazing.swagger.models.OrderDto
import com.unical.amazing.swagger.models.ProductDto
import com.unical.amazing.viewmodel.auth.AuthViewModel
import io.swagger.client.apis.OrderApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.unical.amazing.swagger.models.ProductWithQuantity
import com.unical.amazing.swagger.models.ProductWithQuantityDto

class OrdersHistoryViewModel(context: Context) : ViewModel() {
    private val _orders = MutableStateFlow<List<OrderDto>?>(null)
    val orders: StateFlow<List<OrderDto>?> get() = _orders
    private val token = AuthViewModel(context).getToken()
    private val ord = OrderApi(context)
    private var response: List<Map<String, Any?>>? = null
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val orderDtoAdapter: JsonAdapter<OrderDto> = moshi.adapter(OrderDto::class.java)
    private val productDtoAdapter: JsonAdapter<ProductDto> = moshi.adapter(ProductDto::class.java)

    init {
        fetchUsrOrders()
    }

    private fun fetchUsrOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                response = token?.let { ord.getAllByUser(it) }
                println("Raw Response: $response")

                // Mappa temporanea per raccogliere i prodotti e le quantità per ogni ordine
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

                println("Mapped OrderDtos: $orderDtos")
                _orders.value = orderDtos
            } catch (e: Exception) {
                e.printStackTrace()
                _orders.value = emptyList()
            }
        }
    }





}
