package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.data.network.error.ErrorRetrofitFactory
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): T? {
    try {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw HttpException(response)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        throw ErrorRetrofitFactory(e).crearError()
    }
}
