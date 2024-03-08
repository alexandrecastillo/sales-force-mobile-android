package biz.belcorp.salesforce.core.entities.politicstermsconditions

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class TermsConditionsEntity(
    @Id var id: Long = Constant.NUMBER_ZERO_LONG,
    var termsCode: String = Constant.EMPTY_STRING,
    var description: String = Constant.EMPTY_STRING,
    var url: String = Constant.EMPTY_STRING,
    var position: Int = Constant.NUMBER_ZERO,
    var active: Boolean = false

)

