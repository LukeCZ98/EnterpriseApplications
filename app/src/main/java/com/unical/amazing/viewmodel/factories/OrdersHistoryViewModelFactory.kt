package com.unical.amazing.viewmodel.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unical.amazing.viewmodel.account.OrdersHistoryViewModel

class OrdersHistoryViewModelFactory(private val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrdersHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrdersHistoryViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}