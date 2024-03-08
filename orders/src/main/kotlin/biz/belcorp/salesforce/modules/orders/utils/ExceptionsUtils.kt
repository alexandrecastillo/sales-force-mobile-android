package biz.belcorp.salesforce.modules.orders.utils

import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException

private const val GENERIC_ERROR = "Se ha producido un error de aplicación"
private const val NETWORK_ERROR = "No hay conexión a internet"

fun Throwable.customMessage(): String {
    return when (this) {
        is NetworkConnectionException -> NETWORK_ERROR
        else -> GENERIC_ERROR
    }
}
