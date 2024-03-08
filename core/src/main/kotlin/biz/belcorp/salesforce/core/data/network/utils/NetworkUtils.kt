package biz.belcorp.salesforce.core.data.network.utils

import android.content.Context
import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.utils.JsonUtil.JsonEncodedDefault
import com.google.firebase.perf.FirebasePerformance
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


const val HEADER_NAME_AUTHORIZATION = "Authorization"
const val HEADER_NAME_ACCESS_TOKEN = "x-access-token"
const val HEADER_NAME_APP_VERSION = "appVersion"
const val HEADER_NAME_CONTENT_TYPE = "Content-Type"

const val HEADER_VALUE_BEARER = "Bearer"
const val HEADER_VALUE_TEXT_PLAIN = "text/plain"

const val CONTENT_TYPE_JSON = "application/json"

const val HEADER_CACHE = "Cache-Control"

const val CACHE_ONLINE_VALUE = "public, max-age=%d"
const val CACHE_OFFLINE_VALUE = "public, only-if-cached, max-stale=%d"

const val CACHE_SIZE = 5 * 1024 * 1024

const val READ_TIMEOUT = 1L
const val CONNECT_TIMEOUT = 1L

fun createOkHttpClient(
    cache: Cache? = null,
    f: (Request.Builder.() -> Request.Builder)? = null
): OkHttpClient {
    return OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor { chain ->
            val request = chain.request()
            val requestBuilder = request.newBuilder()
            val newRequestBuilder = (f?.invoke(requestBuilder) ?: requestBuilder)
            val newRequest = newRequestBuilder.build()
            traceHttpRequest(newRequest) { chain.proceed(newRequest) }
        }
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
        .build()
}

fun createRetrofit(httpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(JsonEncodedDefault.asConverterFactory(MediaType.get(CONTENT_TYPE_JSON)))
        .client(httpClient)
        .build()
}

fun createRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

fun createCache(context: Context): Cache {
    val cacheSize = CACHE_SIZE.toLong()
    return Cache(context.cacheDir, cacheSize)
}

fun createRetrofitNew(httpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(JsonEncodedDefault.asConverterFactory(MediaType.get(CONTENT_TYPE_JSON)))
        .client(httpClient)
        .build()
}

fun Request.Builder.addAuthorizationHeader(token: String?) = apply {
    token?.also { header(HEADER_NAME_AUTHORIZATION, "$HEADER_VALUE_BEARER $it") }
}

fun Request.Builder.addAccessTokenHeader(token: String?) = apply {
    token?.also { header(HEADER_NAME_ACCESS_TOKEN, "$HEADER_VALUE_BEARER $it") }
}

fun Request.Builder.addPlainTextHeader() = apply {
    header(HEADER_NAME_CONTENT_TYPE, HEADER_VALUE_TEXT_PLAIN)
}

fun Request.Builder.addVersionName(name: String) = apply {
    header(HEADER_NAME_APP_VERSION, name)
}

fun Request.Builder.addCacheHeader(isOnline: Boolean, configPref: ConfigSharedPreferences) = apply {
    if (isOnline) {
        header(HEADER_CACHE, String.format(CACHE_ONLINE_VALUE, configPref.maxAge)).build()
    } else {
        header(HEADER_CACHE, String.format(CACHE_OFFLINE_VALUE, configPref.maxStale)).build()
    }
}

private fun traceHttpRequest(request: Request, proceed: () -> Response): Response {
    val graphqlUrl = getGraphqlUrl(request) ?: return proceed.invoke()
    val metric = FirebasePerformance.getInstance().newHttpMetric(graphqlUrl, request.method())
    metric.start()
    metric.setRequestPayloadSize(request.body()?.contentLength() ?: Constant.NUMBER_ZERO_LONG)
    val response = proceed.invoke()
    metric.setHttpResponseCode(response.code())
    metric.setResponsePayloadSize(response.body()?.contentLength() ?: Constant.NUMBER_ZERO_LONG)
    metric.stop()
    return response
}

private fun getGraphqlUrl(request: Request): String? {
    val url = request.url().url().path?.takeIf { it.endsWith(Constant.GRAPHQL) } ?: return null
    val function = request.stringBody()?.split(Constant.BLANK_SPACE)?.getOrNull(Constant.NUMBER_TWO)
        ?: return null
    return "$url${Constant.DELIMITER}$function"
}

private fun Request.stringBody(): String? {
    return try {
        val buffer = Buffer()
        body()?.writeTo(buffer)
        buffer.readUtf8()
    } catch (e: IOException) {
        null
    }
}
