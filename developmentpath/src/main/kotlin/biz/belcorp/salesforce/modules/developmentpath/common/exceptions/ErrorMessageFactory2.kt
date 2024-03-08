package biz.belcorp.salesforce.modules.developmentpath.common.exceptions

import android.content.Context
import biz.belcorp.salesforce.core.data.exceptions.ErrorException
import biz.belcorp.salesforce.core.data.exceptions.VersionamientoException
import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.core.domain.exceptions.UnauthorizedException
import biz.belcorp.salesforce.modules.developmentpath.R


class ErrorMessageFactory2 constructor(private val context: Context) {

    private val genericMessage by lazy { context.getString(R.string.exception_message_generic) }
    private val networkConnection by lazy { context.getString(R.string.exception_message_no_connection) }
    private val userNotFound by lazy { context.getString(R.string.exception_message_user_not_found) }
    private val serviceNotFound by lazy { context.getString(R.string.not_found_service) }
    private val serviceNotAvailable by lazy { context.getString(R.string.not_available_service) }
    private val credencialesInvalidas by lazy { context.getString(R.string.credenciales_invalidas) }
    private val solicitudRechazada by lazy { context.getString(R.string.solicitud_rechazada_mensaje) }

    fun create(exception: Throwable, init: (CustomErrors.() -> Unit)? = null) =
        CustomErrors().apply {
            init?.invoke(this)
            when (exception) {
                is NetworkConnectionException -> defaultError?.invoke(networkConnection)
                is UserNotFoundException -> defaultError?.invoke(userNotFound)
                is ErrorException -> defaultError?.invoke(exception.message ?: "")
                is ServiceNotFoundException -> defaultError?.invoke(serviceNotFound)
                is UnavailableServiceException -> defaultError?.invoke(serviceNotAvailable)
                is VersionamientoException -> versionamientoError?.invoke("")
                is ServerException -> defaultError?.invoke(exception.message ?: "")
                is UnauthorizedException -> unauthorizedError?.invoke("") ?: defaultError?.invoke(
                    exception.message ?: ""
                )
                is CredencialesInvalidasException -> defaultError?.invoke(credencialesInvalidas)
                is PostulanteRechazadaException -> solicitudRechazadaError?.invoke(
                    exception.message ?: solicitudRechazada
                )
                else -> defaultError?.invoke(genericMessage)
            }
        }

    class CustomErrors {

        var defaultError: ((String) -> Unit)? = null
        var versionamientoError: ((String) -> Unit)? = null
        var unauthorizedError: ((String) -> Unit)? = null
        var solicitudRechazadaError: ((String) -> Unit)? = null

        fun onDefaultError(defaultError: ((String) -> Unit)?) {
            this.defaultError = defaultError
        }

    }

}
