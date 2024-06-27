package com.unical.amazing.swagger.apis

import android.content.Context
import com.unical.amazing.R
import com.unical.amazing.model.settings.HOST_URL
import io.swagger.client.infrastructure.ApiClient
import io.swagger.client.infrastructure.ClientError
import io.swagger.client.infrastructure.ClientException
import io.swagger.client.infrastructure.Informational
import io.swagger.client.infrastructure.Redirection
import io.swagger.client.infrastructure.RequestConfig
import io.swagger.client.infrastructure.RequestMethod
import io.swagger.client.infrastructure.ResponseType
import io.swagger.client.infrastructure.ServerError
import io.swagger.client.infrastructure.ServerException
import io.swagger.client.infrastructure.Success
import io.swagger.client.infrastructure.createSecureClient

class AuthApi(context: Context, // Aggiungi il Context come parametro
              basePath: String = "https://$HOST_URL:8443/"
             ) : ApiClient(basePath, createSecureClient(context, R.raw.truststore,"progettoea")) {

    /**
     * 
     * 
     * @param body  
     * @return kotlin.Any
     */
    @Suppress("UNCHECKED_CAST")
    fun login(body: kotlin.collections.Map<String, String>): kotlin.Any {
        val localVariableBody: kotlin.Any? = body
        val localVariableConfig = RequestConfig(
                RequestMethod.POST,
                "/auth/login"
        )
        val response = request<kotlin.Any>(
                localVariableConfig, localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as kotlin.Any
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }


    @Suppress("UNCHECKED_CAST")
    fun register(body: Map<String, String>): Int {
        val localVariableBody: Any? = body
        val localVariableConfig = RequestConfig(
            RequestMethod.POST,
            "/auth/register"
        )

        val response = request<Any>(localVariableConfig, localVariableBody)

        return when (response.responseType) {
            ResponseType.Success -> {
                (response as Success<*>).statusCode // Restituisce il codice di stato per il caso di successo
            }
            ResponseType.Informational -> {
                (response as Informational<*>).statusCode // Restituisce il codice di stato per il caso informazionale
            }
            ResponseType.Redirection -> {
                (response as Redirection<*>).statusCode // Restituisce il codice di stato per il caso di redirezione
            }
            ResponseType.ClientError -> {
                val statusCode = (response as ClientError<*>).statusCode
                val errorBody = response.body as? String
                val message = errorBody ?: "Client error"
                println("AuthApi, $message")
                throw ClientException("Status code: $statusCode, message: $message")
            }
            ResponseType.ServerError -> {
                val statusCode = (response as ServerError<*>).statusCode
                val errorMessage = response.message ?: "Server error"
                println("AuthApi, $errorMessage")
                throw ServerException("Status code: $statusCode, message: $errorMessage")
            }
        }
    }









}
