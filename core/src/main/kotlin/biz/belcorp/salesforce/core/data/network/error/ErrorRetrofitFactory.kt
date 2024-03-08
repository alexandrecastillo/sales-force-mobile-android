package biz.belcorp.salesforce.core.data.network.error

import android.util.Log
import biz.belcorp.salesforce.core.data.exceptions.ServerErrorException
import biz.belcorp.salesforce.core.data.exceptions.VersionamientoException
import biz.belcorp.salesforce.core.data.network.dto.ErrorBody
import biz.belcorp.salesforce.core.data.network.dto.LegacyErrorBody
import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.core.domain.exceptions.UnauthorizedException
import biz.belcorp.salesforce.core.utils.safeJsonParse
import retrofit2.HttpException
import java.io.IOException

class ErrorRetrofitFactory(private val e: Throwable) {

    fun crearError(): Throwable {
        return when (e) {
            is HttpException -> buildExceptionByType(e)
            is IOException -> NetworkConnectionException()
            else -> ServerErrorException(TEXT_ERROR, e)
        }
    }

    private fun buildExceptionByType(exception: HttpException): Exception {
        return when (exception.code()) {
            UNAUTHORIZED_CODE,
            FORBIDDEN_CODE -> UnauthorizedException()
            else -> buildException(exception) ?: ServerErrorException(TEXT_ERROR, e)
        }
    }

    private fun buildException(exception: HttpException): Exception? {
        return when (val errorBody = parseErrorBody(exception)) {
            is ErrorBody -> buildExceptionForErrorBody(errorBody)
            is LegacyErrorBody -> buildExceptionForLegacyErrorBody(errorBody)
            else -> null
        }
    }

    private fun buildExceptionForErrorBody(errorBody: ErrorBody): Exception? {
        Log.e("Error body", errorBody.result.exceptionMessage)
        return when (errorBody.result.exceptionMessage) {
            TOKEN_EXPIRED_MESSAGE -> UnauthorizedException()
            else -> null
        }
    }

    private fun buildExceptionForLegacyErrorBody(errorBody: LegacyErrorBody): Exception? {
        return when (errorBody.code) {
            VERSION_ERROR -> VersionamientoException()
            UNAUTHORIZED_ERROR -> UnauthorizedException()
            else -> null
        }
    }

    private fun parseErrorBody(exception: HttpException): Any? {
        return try {
            val errorBody = exception.response()?.errorBody()?.string()
            safeJsonParse(LegacyErrorBody.serializer(), errorBody)
                ?: safeJsonParse(ErrorBody.serializer(), errorBody)
        } catch (e: Exception) {
            null
        }
    }

    companion object {

        const val VERSION_ERROR = "002"
        const val UNAUTHORIZED_ERROR = "003"

        const val TEXT_ERROR = "Ocurrió un error en la sincronización de datos."

        const val TOKEN_EXPIRED_MESSAGE = "Token has expired."

        const val UNAUTHORIZED_CODE = 401
        const val FORBIDDEN_CODE = 403

    }

}
