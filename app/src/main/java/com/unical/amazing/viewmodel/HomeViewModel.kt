package com.unical.amazing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unical.amazing.model.Product
import com.unical.amazing.model.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                // Esegui le operazioni di rete su un thread separato
                val productList = ProductRepository.fetchProducts()
                _products.value = productList
            } catch (e: Exception) {
                // Gestisci gli errori di rete
                // Ad esempio, puoi impostare un valore di fallback o mostrare un messaggio di errore
            }
        }
    }
}



