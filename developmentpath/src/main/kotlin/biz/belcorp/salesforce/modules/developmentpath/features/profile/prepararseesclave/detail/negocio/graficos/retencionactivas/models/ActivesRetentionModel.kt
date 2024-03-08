package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.models

import biz.belcorp.salesforce.core.constants.Constant

class ActivesRetentionModel(
    var campaign: String = Constant.EMPTY_STRING,
    var activesReal: Int = Constant.NUMBER_ZERO,
    var activesLastYear: Int = Constant.NUMBER_ZERO
)
