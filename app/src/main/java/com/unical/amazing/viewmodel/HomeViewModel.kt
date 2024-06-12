package com.unical.amazing.viewmodel

import android.content.Context
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

class HomeViewModel(context: Context) : ViewModel() {



    var productList by mutableStateOf(emptyList<ProductDto>())
    val productDataFlow: Flow<List<ProductDto>> = automaticRetrieve()
    val context = context
    private fun automaticRetrieve() : Flow<List<ProductDto>> {
        return flow {
            while (true) {
                try {
                    // Ottieni la lista dei prodotti dall'API
                    val products = ProductApi(context).getAll().toList()
                    emit(products)
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



    var productsList by mutableStateOf(emptyList<ProductDto>())
        private set

    fun searchProducts(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                productsList = ProductApi(context).findByTitleLike(title).toList()         //restituisce array vuoto
            } catch (e: Exception) {
                // Handle error
            }
        }
    }


}
