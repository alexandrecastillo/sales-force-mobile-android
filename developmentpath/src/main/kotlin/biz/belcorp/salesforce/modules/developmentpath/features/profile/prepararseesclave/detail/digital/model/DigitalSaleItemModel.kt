package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model

import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.helper.DigitalViewTypeIdentifier

data class DigitalSaleItemModel(
    val id: Int = Constant.NUMBER_ONE,
    val description: String? = Constant.EMPTY_STRING,
    val campaignList: List<DigitalSaleCampaignModel>,
    @DigitalViewTypeIdentifier val type: Int = 0
)
