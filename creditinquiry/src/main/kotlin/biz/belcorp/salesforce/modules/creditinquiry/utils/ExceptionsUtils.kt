package biz.belcorp.salesforce.modules.creditinquiry.utils

import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.modules.creditinquiry.core.data.exception.ErrorException
import biz.belcorp.salesforce.modules.creditinquiry.core.data.exception.UserNotFoundException


private const val GENERIC_ERROR = "Se ha producido un error de aplicación"
private const val NETWORK_ERROR = "No hay conexión a internet"
private const val USER_NOT_FOUND_ERROR =
    "No se puede obtener los datos de usuario. Revisar su conección a internet"


fun Throwable.customMessage(): String {
    return when (this) {
        is NetworkConnectionException -> NETWORK_ERROR
        is UserNotFoundException -> USER_NOT_FOUND_ERROR
        is ErrorException -> this.message.toString()
        else -> GENERIC_ERROR
    }
}
