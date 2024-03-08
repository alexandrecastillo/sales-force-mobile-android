package biz.belcorp.salesforce.core.entities.politicstermsconditions

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class ApproveTermsConditionsEntity(
    @Id var id: Long = Constant.NUMBER_ZERO_LONG,
    var campaign: String = Constant.EMPTY_STRING,
    var termsCode: String = Constant.EMPTY_STRING,
    var checked: Boolean = false,
    var active: Boolean = false

)
