package com.unical.amazing.model

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Serializable
data class Product(val id: Int, val title: String, val price: Double)

object ProductRepository {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }

    suspend fun fetchProducts(): List<Product> {
        return try {
            withContext(Dispatchers.IO) {
                client.get("http://192.168.1.160:8010/v1/products").body()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
