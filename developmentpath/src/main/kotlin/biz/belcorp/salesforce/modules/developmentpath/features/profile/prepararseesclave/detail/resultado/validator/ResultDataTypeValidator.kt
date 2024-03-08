package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.validator

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.STRING_ZERO_DECIMAL
import biz.belcorp.salesforce.core.constants.Constant.UNDERSCORE
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import java.text.NumberFormat
import java.util.*

class ResultDataTypeValidator {

    fun valueDouble(valor: Double?): String {
        return when (valor) {
            null -> UNDERSCORE
            ZERO_DECIMAL -> STRING_ZERO_DECIMAL
            else -> NumberFormat.getNumberInstance(Locale.US).format(valor).toString()
        }
    }

    fun valueInt(valor: Int?) = valor?.let { "$valor" } ?: run { UNDERSCORE }

    fun valueString(valor: String?) = valor ?: UNDERSCORE

    fun validatePercentage(valor: Int?) = valor?.let { "${valor}%" } ?: run { UNDERSCORE }

    fun valueFloat(valor: Float?): String {
        return when (valor) {
            null -> UNDERSCORE
            ZERO_DECIMAL.toFloat() -> "$NUMBER_ZERO"
            else -> valor.toString()
        }
    }
}