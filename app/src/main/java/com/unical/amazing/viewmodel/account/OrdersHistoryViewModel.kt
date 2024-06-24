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

class OrdersHistoryViewModel(context: Context) : ViewModel() {
    private val _orders = MutableStateFlow<List<OrderDto>?>(null)
    val orders: StateFlow<List<OrderDto>?> get() = _orders
    private val token = AuthViewModel(context).getToken()
    private val ord = OrderApi(context)

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
            try {  //TODO() -> mappare ed estrarre bene i dati,sul backend funziona
                val response: Map<String, Any?>? = token?.let { ord.getAllByUser(it) } as? Map<String, Any?>
                val orderDtos: List<OrderDto> = response?.get("quantities")?.let { quantities ->
                    (quantities as? List<Map<String, Any?>>)?.mapNotNull { quantity ->
                        val product = quantity["product"] as? Map<String, Any?>
                        product?.let {
                            val productJson = moshi.adapter(Map::class.java).toJson(product)
                            val productDto = productDtoAdapter.fromJson(productJson)
                            OrderDto(
                                id = quantity["id"] as? Int ?: return@mapNotNull null,
                                product = listOfNotNull(productDto),
                                quantity = quantity["quantity"] as? Int ?: return@mapNotNull null
                            )
                        }
                    } ?: emptyList()
                } ?: emptyList()
                _orders.value = orderDtos
            } catch (e: Exception) {
                e.printStackTrace()
                _orders.value = emptyList()
            }
        }
    }
}