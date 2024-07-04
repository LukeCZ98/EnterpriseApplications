package com.unical.amazing.viewmodel.cart

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unical.amazing.swagger.models.CheckoutDto
import com.unical.amazing.swagger.models.ProductWithQuantity
import com.unical.amazing.viewmodel.auth.AuthViewModel
import com.unical.amazing.swagger.apis.OrderApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(context: Context) : ViewModel() {
    private val ord = OrderApi(context)
    private val token = AuthViewModel(context).getToken()
    private val _checkoutResult = MutableStateFlow<Boolean?>(null)
    val checkoutResult: StateFlow<Boolean?> get() = _checkoutResult

    fun checkout(order: List<ProductWithQuantity>) {
        viewModelScope.launch(Dispatchers.IO) {
            val neworder = order.mapNotNull {
                it.product.id?.let { it1 ->
                    CheckoutDto(
                        productId = it1,
                        quantity = it.quantity
                    )
                }
            }
            println(neworder)
            try {
                _checkoutResult.value = true
                val response = token?.let { ord.checkout(it, neworder) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }




}