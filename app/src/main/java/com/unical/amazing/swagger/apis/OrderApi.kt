
package com.unical.amazing.swagger.apis

import android.content.Context
import com.unical.amazing.R
import com.unical.amazing.model.settings.HOST_URL
import com.unical.amazing.swagger.models.CheckoutDto
import com.unical.amazing.swagger.models.OrderDto

import io.swagger.client.infrastructure.*

class OrderApi(context: Context, // Aggiungi il Context come parametro
               basePath: String = "https://$HOST_URL:8443/"
              ) : ApiClient(basePath, createSecureClient(context, R.raw.truststore,"progettoea")) {


    /**
     * 
     * 
     * @param body  
     * @return OrderDto
     */
    @Suppress("UNCHECKED_CAST")
    fun checkout(token: String,body: List<CheckoutDto>): Any {
        val headers = mapOf("Authorization" to "Bearer $token")
        val localVariableBody: Any = body
        val localVariableConfig = RequestConfig(
                RequestMethod.POST,
                "/orders/checkout",
                headers = headers
        )
        val response = request<OrderDto>(
                localVariableConfig, localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as OrderDto
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }

    /**
     * 
     * 
     * @param userId  
     * @return kotlin.Array<OrderDto>
     */
    @Suppress("UNCHECKED_CAST")
    fun getAllByUser(token: String): List<Map<String, Any?>> {
        val headers = mapOf("Authorization" to "Bearer $token")
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/orders/all",
            headers = headers
        )
        val response = request<List<Map<String, Any?>>>(
            localVariableConfig
        )


        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as List<Map<String, Any?>>
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> {
                throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            }
            ResponseType.ServerError -> {
                throw ServerException((response as ServerError<*>).message ?: "Server error")
            }
        }
    }



}
