package com.unical.amazing.viewmodel.auth

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unical.amazing.swagger.apis.AuthApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(context : Context):ViewModel() {


    private val authApi = AuthApi(context)

    var authResponse by mutableStateOf<Map<String, Any?>?>(null)
        private set

    fun login(body: Map<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = authApi.login(body) as? Map<String, Any?>
                authResponse = result
            } catch (e: Exception) {
                e.printStackTrace()
                authResponse = null
            }
        }
    }
}