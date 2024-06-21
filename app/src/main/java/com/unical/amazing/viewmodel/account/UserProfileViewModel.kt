package com.unical.amazing.viewmodel.account

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unical.amazing.swagger.models.UserUpdDto
import com.unical.amazing.viewmodel.auth.AuthViewModel
import io.swagger.client.apis.UserApi
import io.swagger.client.models.UserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserProfileViewModel(context: Context) : ViewModel() {
    private val _user = MutableStateFlow<UserDto?>(null)
    val user: StateFlow<UserDto?> get() = _user
    private val token = AuthViewModel(context).getToken()
    private val usr = UserApi(context)

    init {
        fetchUserData()
    }



    private fun fetchUserData() {
        // Simuliamo una chiamata di rete per ottenere i dati dell'utente,da aggiornare con codice connessione a db
        viewModelScope.launch(Dispatchers.IO) {
            val user = token?.let { usr.account(it) }
            _user.value = user
        }
    }


    suspend fun updateUser(updatedUser: UserDto): String? {
        val addr = updatedUser.addresses?.get(0)?.address.toString()
        val phone = updatedUser.phone
        val cap = updatedUser.CAP
        val city = updatedUser.city
        val country = updatedUser.country
        val newusr = UserUpdDto(phone,addr,city,country,cap)
        if (token != null) {
            return withContext(Dispatchers.IO){
                try {
                    val response = usr.update(token,newusr)
//                    println("Response di updateuser: $response")
                    if (response == 200) {
                        _user.value = updatedUser
                        "200"
                    }
                    else{
                        null
                    }
                }
                catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
        else{
            return null
        }
    }


}
