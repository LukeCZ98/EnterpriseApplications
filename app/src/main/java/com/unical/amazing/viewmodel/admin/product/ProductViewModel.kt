package com.unical.amazing.viewmodel.admin.product

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unical.amazing.swagger.apis.ProductApi
import com.unical.amazing.swagger.models.ProductDto
import com.unical.amazing.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(context: Context) : ViewModel()  {
    private val _prods = MutableStateFlow<Array<ProductDto>?>(null)
    val prods: StateFlow<Array<ProductDto>?> get() = _prods
    val prodapi = ProductApi(context)
    private val token = AuthViewModel(context).getToken()

    fun fetchall(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response = prodapi.getAll()
                _prods.value = response
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addProduct(product: ProductDto) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = token?.let { prodapi.addProduct(token,product) }
                fetchall()
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun deleteProduct(product: ProductDto){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = token?.let { prodapi.deleteProduct(token,product) }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateProduct(product: ProductDto){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = token?.let { prodapi.updateProduct(token,product) }
                fetchall()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }





}