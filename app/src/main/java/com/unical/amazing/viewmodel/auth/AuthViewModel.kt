package com.unical.amazing.viewmodel.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import com.unical.amazing.swagger.apis.AuthApi
import io.ktor.http.cio.Response
import io.swagger.client.infrastructure.ResponseType
import io.swagger.client.infrastructure.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(context: Context) : ViewModel() {

    private val authApi = AuthApi(context)

    suspend fun login(body: Map<String, String>): Map<String, Any?>? {
        return withContext(Dispatchers.IO) {
            try {
                authApi.login(body) as? Map<String, Any?>
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





}