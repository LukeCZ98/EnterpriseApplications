package com.unical.amazing.viewmodel.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.unical.amazing.swagger.apis.AuthApi
import io.ktor.http.cio.Response
import io.swagger.client.infrastructure.ResponseType
import io.swagger.client.infrastructure.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(context: Context) : ViewModel() {

    private val authApi = AuthApi(context)
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)


    suspend fun login(body: Map<String, String>): Map<String, Any?>? {
        return withContext(Dispatchers.IO) {
            try {
                val response: Map<String, Any?>? = authApi.login(body) as? Map<String, Any?>
                response?.let {
                    val token = it["jwt"] as? String    //DOPO AVER EFFETTUATO IL LOGIN, SALVA IL TOKEN IN LOCALE
                    token?.let { saveToken(it) }        //PER ESSERE RIUTILIZZATO NELL'APP
                }
                response
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }



    suspend fun register(body: Map<String, String>): String? {
        return withContext(Dispatchers.IO) {
            try {
                // Esegui la richiesta di registrazione
                val response: Any = authApi.register(body)

                // Controlla se la risposta è un successo e il codice di stato è 200
                if (response is Map<*, *> && response["statusCode"] == 200) {
                    "200"
                } else {
                    // Puoi adattare cosa restituire in altri casi di successo
                    response.toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }


    private fun saveToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove("auth_token").apply()
    }


}