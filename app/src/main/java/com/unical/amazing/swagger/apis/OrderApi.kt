/**
 * OpenAPi NerdWarehouse
 * OpenApi documentation for Spring Security
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package io.swagger.client.apis

import io.swagger.client.models.OrderDto

import io.swagger.client.infrastructure.*

class OrderApi(basePath: kotlin.String = "http://localhost:8010/") : ApiClient(basePath) {

    /**
     * 
     * 
     * @param body  
     * @return OrderDto
     */
    @Suppress("UNCHECKED_CAST")
    fun addOrder(body: OrderDto): OrderDto {
        val localVariableBody: kotlin.Any? = body
        val localVariableConfig = RequestConfig(
                RequestMethod.POST,
                "/v1/orders"
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
    fun getAllByUser(userId: kotlin.Long): kotlin.Array<OrderDto> {
        val localVariableConfig = RequestConfig(
                RequestMethod.GET,
                "/v1/orders/{userId}".replace("{" + "userId" + "}", "$userId")
        )
        val response = request<kotlin.Array<OrderDto>>(
                localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as kotlin.Array<OrderDto>
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }
}
