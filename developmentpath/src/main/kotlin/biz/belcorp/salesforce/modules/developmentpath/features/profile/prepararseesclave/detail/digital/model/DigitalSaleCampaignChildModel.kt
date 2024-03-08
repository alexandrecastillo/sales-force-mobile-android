package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model

import androidx.annotation.ColorRes
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

data class DigitalSaleCampaignChildModel(
    @ColorRes val colorRes: Int,
    val number: String = Constant.EMPTY_STRING,
    val value: String? = Constant.EMPTY_STRING
)
