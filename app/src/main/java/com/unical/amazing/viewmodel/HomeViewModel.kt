package com.unical.amazing.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unical.amazing.swagger.apis.ProductApi
import com.unical.amazing.swagger.models.ProductDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {
    var productList by mutableStateOf(emptyList<ProductDto>())
    val productDataFlow: Flow<List<ProductDto>> = automaticRetrieve()
    private fun automaticRetrieve() : Flow<List<ProductDto>> {
        return flow {
            while (true) {
                try {
                    // Ottieni la lista dei prodotti dall'API
                    val products = ProductApi().getAll().toList()
                    emit(products)
                    println("Products: $products")
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Gestisci gli errori di connessione o altri problemi
                    emit(emptyList())
                }
                delay(30000)
            }
        }.flowOn(Dispatchers.IO)
    }

    init {
        viewModelScope.launch {
            productDataFlow.collect { products ->
                productList = products
            }
        }
    }
}
