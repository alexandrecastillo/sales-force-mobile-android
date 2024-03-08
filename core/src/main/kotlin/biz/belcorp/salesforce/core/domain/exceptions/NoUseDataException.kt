package biz.belcorp.salesforce.core.domain.exceptions


class NoUseDataException : Exception(MESSAGE) {

    companion object {

        const val MESSAGE = "No se pudo obtener datos del usuario"

    }

}
