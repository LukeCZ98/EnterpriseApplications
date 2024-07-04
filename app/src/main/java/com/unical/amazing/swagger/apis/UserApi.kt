package com.unical.amazing.swagger.apis

import android.content.Context
import com.unical.amazing.R
import com.unical.amazing.model.settings.HOST_URL
import com.unical.amazing.swagger.models.UserUpdDto
import com.unical.amazing.swagger.models.UserDto
import io.swagger.client.infrastructure.*
import okhttp3.*
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


class UserApi(context: Context,
              basePath: String = "https://$HOST_URL:8443/"
) : ApiClient(basePath, createSecureClient(context, R.raw.truststore,"progettoea")) {

    private val Context = context


    fun getAll(token: String): List<UserDto>? {
        val headers = mapOf("Authorization" to "Bearer $token")
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/user/all",
            headers = headers
        )
        val response = request<Any?>(
            localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> {
                val jsonData = (response as Success<*>).data
                val jsonString = Gson().toJson(jsonData)
                val jsonArray = JsonParser.parseString(jsonString).asJsonArray
                val filteredJsonArray = com.google.gson.JsonArray()

                // Filtra e rimuovi 'addresses' da ogni oggetto utente
                jsonArray.forEach { element ->
                    if (element.isJsonObject) {
                        val userObject = element.asJsonObject
                        userObject.remove("addresses")  // Rimuovi il campo 'addresses'
                        filteredJsonArray.add(userObject)
                    }
                }

                try {
                    Gson().fromJson(filteredJsonArray, Array<UserDto>::class.java).toList()
                } catch (e: JsonSyntaxException) {
                    // Gestisci l'eccezione qui se la deserializzazione fallisce
                    e.printStackTrace()
                    null
                }
            }
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }



    fun delUser(token: String,userid:Long):Any?{
        val headers = mapOf("Authorization" to "Bearer $token")
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/user/del/$userid",
            headers = headers
        )
        val response = request<Any?>(
            localVariableConfig
        )


        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data
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


    fun getUserOrders(token: String,id: Long): List<Map<String, Any?>> {
        val headers = mapOf("Authorization" to "Bearer $token")
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/orders/find/$id",
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




    fun delUserOrd(token: String,ordid:Long):Any?{
        val headers = mapOf("Authorization" to "Bearer $token")
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/orders/del/$ordid",
            headers = headers
        )
        val response = request<Any?>(
            localVariableConfig
        )


        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data
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



















//UTILIZZATO PER AREA PERSONALE UTENTE
    @Suppress("UNCHECKED_CAST")
    fun account(token: String): UserDto {
        val headers = mapOf("Authorization" to "Bearer $token")
        val localVariableConfig = RequestConfig(
            RequestMethod.GET,
            "/auth/me",
            headers = headers
            )
        val response = request<UserDto>(
            localVariableConfig
        )

        return when (response.responseType) {
            ResponseType.Success -> (response as Success<*>).data as UserDto
            ResponseType.Informational -> TODO()
            ResponseType.Redirection -> TODO()
            ResponseType.ClientError -> throw ClientException((response as ClientError<*>).body as? String ?: "Client error")
            ResponseType.ServerError -> throw ServerException((response as ServerError<*>).message ?: "Server error")
        }
    }




    fun update(token: String, body: UserUpdDto): Int {
        val gson = Gson()

        // Converti il corpo in JSON
        val jsonBody = gson.toJson(body)
        val requestBody = RequestBody.create("application/json".toMediaType(), jsonBody)

        // Carica il tuo truststore (assumi che il percorso sia configurato correttamente)
        val client = createHttpClientWithTrustStore(Context,"progettoea")

        // Costruisci la richiesta
        val request = Request.Builder()
            .url("https://$HOST_URL:8443/auth/update")  // Usa HTTPS
            .post(requestBody)
            .addHeader("Authorization", "Bearer $token")
            .build()

        try {
            // Esegui la richiesta
            client.newCall(request).execute().use { response ->
                return when (response.code) {
                    200 -> 200
                    in 100..199 -> throw IOException("Informational response code: ${response.code}")
                    in 300..399 -> throw IOException("Redirection response code: ${response.code}")
                    in 400..499 -> throw ClientException(response.body?.string() ?: "Client error")
                    in 500..599 -> throw ServerException(response.message ?: "Server error")
                    else -> throw IOException("Unexpected response code: ${response.code}")
                }
            }
        } catch (e: IOException) {
            throw e
        }
    }




    private fun createHttpClientWithTrustStore(context: Context, trustStorePassword: String): OkHttpClient {
        val trustStore = KeyStore.getInstance("BKS")

        // Usa Resources.openRawResource() per ottenere l'InputStream
        context.resources.openRawResource(R.raw.truststore).use { inputStream ->
            trustStore.load(inputStream, trustStorePassword.toCharArray())
        }

        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(trustStore)
        val trustManagers = trustManagerFactory.trustManagers

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagers, null)

        val trustManager = trustManagers[0] as X509TrustManager

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .hostnameVerifier { _, _ -> true }
            .build()
    }




}
