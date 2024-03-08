package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model

import biz.belcorp.salesforce.core.constants.Constant

data class DigitalSaleContainerModel(
    val list: List<DigitalSaleModel>,
    val activesTitle: String = Constant.EMPTY_STRING
)
