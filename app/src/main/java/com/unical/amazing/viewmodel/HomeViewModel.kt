package com.unical.amazing.viewmodel


import com.unical.amazing.model.HomeItem
import com.unical.amazing.model.HomeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel {
    private val homeModel = HomeModel()

    // Dichiarare homeItems come MutableStateFlow
    private val _homeItems: MutableStateFlow<List<HomeItem>> = MutableStateFlow(emptyList())
    val homeItems: StateFlow<List<HomeItem>> = _homeItems

    fun fetchHomeItems() {
        // Aggiornare il valore di _homeItems invece di homeItems
        _homeItems.value = homeModel.getHomeItems()
    }
}


