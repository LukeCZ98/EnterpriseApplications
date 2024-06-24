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
package com.unical.amazing.swagger.models


/**
 * 
 * @param title 
 * @param price 
 * @param description 
 * @param available 
 * @param userId
 * @param img_url
 */
data class ProductDto (
    val id: kotlin.Long? = null,
    val title: kotlin.String? = null,
    val price: kotlin.Double,
    val description: kotlin.String? = null,
    val available: kotlin.Boolean? = null,
    val img_url: kotlin.String? = null
) {
}