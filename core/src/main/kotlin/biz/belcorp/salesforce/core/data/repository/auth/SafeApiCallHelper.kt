package biz.belcorp.salesforce.core.data.repository.auth

import biz.belcorp.salesforce.core.data.network.error.capturarError
import biz.belcorp.salesforce.core.domain.exceptions.UnauthorizedException
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import biz.belcorp.salesforce.core.utils.safeApiCall as safeCall


class SafeApiCallHelper(private val authTokenRenewer: AuthTokenRenewer) {

    suspend fun <T : Any> safeBelcorpApiCall(call: suspend () -> Response<T>): T? {
        return genericSafeApiCall(call) {
            authTokenRenewer.renewToken()
        }
    }

    suspend fun <T : Any> safeLegacyApiCall(call: suspend () -> Response<T>): T? {
        return genericSafeApiCall(call) {
            authTokenRenewer.renewLegacyTokenForced()
        }
    }

    private suspend fun <T : Any> genericSafeApiCall(
        call: suspend () -> Response<T>,
        f: suspend () -> Boolean
    ): T? {
        return try {
            safeCall(call)
        } catch (e: Exception) {
            if (e is UnauthorizedException
                && f.invoke()
            ) {
                safeCall(call)
            } else {
                throw e
            }
        }
    }

    fun <T : Any> safeSingleApiCall(call: () -> Single<T>): Single<T> {
        return call.invoke()
            .capturarError()
            .onErrorResumeNext { exception ->
                if (exception is UnauthorizedException && renewLegacyToken()) {
                    call.invoke()
                        .capturarError()
                } else {
                    Single.error(exception)
                }
            }
    }

    fun safeCompletableApiCall(call: () -> Completable): Completable {
        return call.invoke()
            .capturarError()
            .onErrorResumeNext { exception ->
                if (exception is UnauthorizedException && renewLegacyToken()) {
                    call.invoke()
                        .capturarError()
                } else {
                    Completable.error(exception)
                }
            }
    }

    private fun renewLegacyToken(): Boolean {
        return runBlocking {
            authTokenRenewer.renewLegacyTokenForced()
        }
    }

}
