package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model

import biz.belcorp.salesforce.core.constants.Constant

data class LevelRequestModel(
    val path: String = Constant.EMPTY_STRING,
    val saleAccumulative: String = Constant.EMPTY_STRING,
    val currentLevelOrderRequirement: String = Constant.EMPTY_STRING,
)
