package biz.belcorp.salesforce.core.domain.exceptions


class UnauthorizedException : Exception(MESSAGE) {

    companion object {

        const val MESSAGE = "El usuario o contraseña\ningresado es incorrecto"

    }

}
