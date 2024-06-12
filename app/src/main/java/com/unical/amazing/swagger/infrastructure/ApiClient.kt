package io.swagger.client.infrastructure

import android.content.Context
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.Security
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

open class ApiClient(
    val baseUrl: String,
    protected val httpClient: OkHttpClient = OkHttpClient()  // Modificato da private a protected
) {
    companion object {
        protected const val ContentType = "Content-Type"
        protected const val Accept = "Accept"
        protected const val JsonMediaType = "application/json"
        protected const val FormDataMediaType = "multipart/form-data"
        protected const val XmlMediaType = "application/xml"

        @JvmStatic
        var defaultHeaders: Map<String, String> by ApplicationDelegates.setOnce(mapOf(ContentType to JsonMediaType, Accept to JsonMediaType))

        @JvmStatic
        val jsonHeaders: Map<String, String> = mapOf(ContentType to JsonMediaType, Accept to JsonMediaType)
    }

    protected inline fun <reified T> requestBody(content: T, mediaType: String = JsonMediaType): RequestBody =
        when {
            content is File -> RequestBody.create(mediaType.toMediaTypeOrNull(), content)

            mediaType == FormDataMediaType -> {
                var builder = FormBody.Builder()
                @Suppress("UNCHECKED_CAST")
                (content as Map<String, String>).forEach { key, value ->
                    builder = builder.add(key, value)
                }
                builder.build()
            }
            mediaType == JsonMediaType -> RequestBody.create(
                mediaType.toMediaTypeOrNull(), Serializer.moshi.adapter(T::class.java).toJson(content)
            )
            mediaType == XmlMediaType -> TODO("xml not currently supported.")

            else -> TODO("requestBody currently only supports JSON body and File body.")
        }

    protected inline fun <reified T : Any?> responseBody(body: ResponseBody?, mediaType: String = JsonMediaType): T? {
        if (body == null) return null
        return when (mediaType) {
            JsonMediaType -> Serializer.moshi.adapter(T::class.java).fromJson(body.source())
            else -> TODO()
        }
    }

    protected inline fun <reified T : Any?> request(requestConfig: RequestConfig, body: Any? = null): ApiInfrastructureResponse<T?> {
        val httpUrl = baseUrl.toHttpUrlOrNull() ?: throw IllegalStateException("baseUrl is invalid.")

        var urlBuilder = httpUrl.newBuilder()
            .addPathSegments(requestConfig.path.trimStart('/'))

        requestConfig.query.forEach { query ->
            query.value.forEach { queryValue ->
                urlBuilder = urlBuilder.addQueryParameter(query.key, queryValue)
            }
        }

        val url = urlBuilder.build()
        val headers = requestConfig.headers + defaultHeaders

        if (headers[ContentType] ?: "" == "") {
            throw kotlin.IllegalStateException("Missing Content-Type header. This is required.")
        }

        if (headers[Accept] ?: "" == "") {
            throw kotlin.IllegalStateException("Missing Accept header. This is required.")
        }

        val contentType = (headers[ContentType] as String).substringBefore(";").toLowerCase()
        val accept = (headers[Accept] as String).substringBefore(";").toLowerCase()

        var request: Request.Builder = when (requestConfig.method) {
            RequestMethod.DELETE -> Request.Builder().url(url).delete()
            RequestMethod.GET -> Request.Builder().url(url)
            RequestMethod.HEAD -> Request.Builder().url(url).head()
            RequestMethod.PATCH -> Request.Builder().url(url).patch(requestBody(body, contentType))
            RequestMethod.PUT -> Request.Builder().url(url).put(requestBody(body, contentType))
            RequestMethod.POST -> Request.Builder().url(url).post(requestBody(body, contentType))
            RequestMethod.OPTIONS -> Request.Builder().url(url).method("OPTIONS", null)
        }

        headers.forEach { header -> request = request.addHeader(header.key, header.value.toString()) }

        val realRequest = request.build()
        val response = httpClient.newCall(realRequest).execute()

        when {
            response.isRedirect -> return Redirection(
                response.code,
                response.headers.toMultimap()
            )
            response.isInformational -> return Informational(
                response.message,
                response.code,
                response.headers.toMultimap()
            )
            response.isSuccessful -> return Success(
                responseBody(response.body, accept),
                response.code,
                response.headers.toMultimap()
            )
            response.isClientError -> return ClientError(
                response.body?.string(),
                response.code,
                response.headers.toMultimap()
            )
            else -> return ServerError(
                null,
                response.body?.string(),
                response.code,
                response.headers.toMultimap()
            )
        }
    }
}




fun createSecureClient(context: Context, keystoreResId: Int, keystorePassword: String): OkHttpClient {
    Security.addProvider(BouncyCastleProvider())

    // Carica il keystore BKS dal file raw
    val keyStore = KeyStore.getInstance("BKS", "BC")
    context.resources.openRawResource(keystoreResId).use { keystoreStream ->
        keyStore.load(keystoreStream, keystorePassword.toCharArray())
    }

    // Crea un TrustManager che usa il nostro KeyStore
    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(keyStore)
    val trustManagers = trustManagerFactory.trustManagers
    val trustManager = trustManagers[0] as X509TrustManager

    // Crea un SSLContext che usa il nostro TrustManager
    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, arrayOf(trustManager), null)

    // Crea e ritorna l'OkHttpClient con SSL configurato
    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustManager)
        .hostnameVerifier { _, _ -> true } // Bypass verifica dell'host  trovare alternativa creando nuovo certificato
        .build()
}



