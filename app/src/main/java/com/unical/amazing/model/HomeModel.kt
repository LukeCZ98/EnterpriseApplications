package com.unical.amazing.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

@Serializable
data class Product(val id: Int, val title: String, val price: Double)

object ProductRepository {
    private val client = OkHttpClient()
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }

    suspend fun fetchProducts(): List<Product> {
        return try {
            withContext(Dispatchers.IO) {
                val request = Request.Builder()
                    .url("http://192.168.1.160:8010/v1/products")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody = response.body?.string()
                    responseBody?.let {
                        json.decodeFromString(it)
                    } ?: emptyList()
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
