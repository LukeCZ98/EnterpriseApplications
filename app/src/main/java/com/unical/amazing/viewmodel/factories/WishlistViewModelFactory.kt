package com.unical.amazing.viewmodel.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unical.amazing.viewmodel.account.WishlistViewModel

class WishlistViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishlistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WishlistViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}