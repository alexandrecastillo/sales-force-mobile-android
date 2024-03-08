package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model

import androidx.annotation.ColorInt
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING

data class GainConsultantModel(val gains: List<GainModel> = emptyList()) {
    val hasGains get() = gains.isNotEmpty()
    var lastGain: GainModel? = null
    var subtitle: String = EMPTY_STRING
}

data class GainModel(
    val campaign: String,
    val amount: String,
    @ColorInt val color: Int,
    val isMaximumGain: Boolean = false
)