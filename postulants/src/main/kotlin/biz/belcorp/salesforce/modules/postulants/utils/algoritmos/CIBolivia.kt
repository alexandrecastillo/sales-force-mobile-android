package biz.belcorp.salesforce.modules.postulants.utils.algoritmos

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING

object CIBolivia {

    private const val CEDULA_IDENTIDAD = "1"
    private const val SUFIJO_EXTRANJERIA = "EXT"

    fun encode(tipoDocumento: String, cedula: String): String {
        return when (tipoDocumento) {
            CEDULA_IDENTIDAD -> cedula
            else -> cedula + SUFIJO_EXTRANJERIA
        }
    }

    fun decode(valor: String?): String? {
        return if (valor.isNullOrBlank()) EMPTY_STRING
        else valor
    }

}
