package com.unical.amazing.viewmodel.admin.product


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.unical.amazing.swagger.models.ProductDto

class SharedProductViewModel : ViewModel() {
    var selectedProduct: ProductDto? by mutableStateOf(null)
}