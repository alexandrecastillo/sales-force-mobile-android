package biz.belcorp.salesforce.modules.digital.features.digital.view.detail.multiprofile.grid

import biz.belcorp.salesforce.core.constants.Constant

class DigitalInfoGridModel(
    val uaLabel: String,
    val value: String = Constant.HYPHEN,
    val valuePercentage: String = Constant.HYPHEN
)
