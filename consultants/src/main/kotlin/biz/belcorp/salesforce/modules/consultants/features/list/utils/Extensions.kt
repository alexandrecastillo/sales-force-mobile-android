package biz.belcorp.salesforce.modules.consultants.features.list.utils

import biz.belcorp.salesforce.modules.consultants.core.domain.constants.Constants


const val PREFIJO_CAMPANIA = "C"

fun String.prefijoConNumeroCampania() =
    if (this.length >= 4) "$PREFIJO_CAMPANIA${this.removeRange(0, 4)}" else this


fun String?.agregarDigitoVerificador(digito: String?) =
    if (digito.isNullOrBlank())
        this ?: Constants.EMPTY_STRING
    else
        "$this - $digito"
