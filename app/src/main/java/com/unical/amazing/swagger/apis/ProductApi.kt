package com.unical.amazing.swagger.apis

import android.content.Context
import com.unical.amazing.R
import com.unical.amazing.swagger.models.ProductDto
import io.swagger.client.infrastructure.ApiClient
import io.swagger.client.infrastructure.ClientError
import io.swagger.client.infrastructure.ClientException
import io.swagger.client.infrastructure.RequestConfig
import io.swagger.client.infrastructure.RequestMethod
import io.swagger.client.infrastructure.ResponseType
import io.swagger.client.infrastructure.ServerError
import io.swagger.client.infrastructure.ServerException
import io.swagger.client.infrastructure.Success
import io.swagger.client.infrastructure.createSecureClient

class ProductApi(
    context: Context, // Aggiungi il Context come parametro
    basePath: String = "https://192.168.1.160:8443/"
) : ApiClient(basePath, createSecureClient(context, R.raw.truststore,"progettoea")) {

    /**
     * 
     * 
     * @param body  
     * @return ProductDto
     */
    @Suppress("UNCHECKED_CAST")
    fun addProduct(body: ProductDto): ProductDto {
        val localVariableBody: kotlin.Any? = body
        val localVariableConfig = RequestConfig(
                RequestMethod.POST,
                "/v1/products"
        )
        val response = request<ProductDto>(
                localVariableConfig, localVariableBody
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as ProductDto
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * 
     * @param productId  
     * @return void
     */
    fun deleteProduct(productId: kotlin.Long): Unit {
        val localVariableConfig = RequestConfig(
                RequestMethod.DELETE,
                "/v1/products/{productId}".replace("{" + "productId" + "}", "$productId")
        )
        val response = request<Any?>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> Unit
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * 
     * @param title  
     * @return kotlin.Array<ProductDto>
     */



    @Suppress("UNCHECKED_CAST")
    fun findByTitleLike(title: kotlin.String): kotlin.Array<ProductDto> {
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/product/find/$title"
        )
        val response = request<kotlin.Array<ProductDto>>(
            localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as kotlin.Array<ProductDto>
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * 
     * @return kotlin.Array<ProductDto>
     */



    @Suppress("UNCHECKED_CAST")
    fun getAll(): kotlin.Array<ProductDto> {
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/product/all"
        )
        val response = request<kotlin.Array<ProductDto>>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as kotlin.Array<ProductDto>
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
     * @return kotlin.Array<ProductDto>
     */
    @Suppress("UNCHECKED_CAST")
    fun getAllByUserId(userId: kotlin.Long): kotlin.Array<ProductDto> {
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/v1/products/{userId}".replace("{" + "userId" + "}", "$userId")
        )
        val response = request<kotlin.Array<ProductDto>>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as kotlin.Array<ProductDto>
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
    /**
     * 
     * 
     * @param id  
     * @return ProductDto
     */
    @Suppress("UNCHECKED_CAST")
    fun getById(id: kotlin.Long): ProductDto {
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/v1/product/{id}".replace("{" + "id" + "}", "$id")
        )
        val response = request<ProductDto>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as ProductDto
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
}
