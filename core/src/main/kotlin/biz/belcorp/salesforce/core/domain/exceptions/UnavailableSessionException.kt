package biz.belcorp.salesforce.core.domain.exceptions

class UnavailableSessionException : Exception(MESSAGE) {

    companion object {

        const val MESSAGE = "Error al obtener la sesion"

    }

}
