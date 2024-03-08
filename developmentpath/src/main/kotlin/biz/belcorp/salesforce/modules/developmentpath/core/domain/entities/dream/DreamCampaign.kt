package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

data class DreamCampaign(
    var id: Long = Constant.NUMBER_ZERO.toLong(),
    var campaign: String? = Constant.EMPTY_STRING,
    var gainAmount: Float = Constant.ZERO_FLOAT,
)
