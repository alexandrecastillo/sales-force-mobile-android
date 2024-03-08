package biz.belcorp.salesforce.core.entities.digital

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.ConsultantEntity
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne


@Entity
data class DigitalConsultantEntity(
    var campaign: String = Constant.EMPTY_STRING,
    var region: String = Constant.EMPTY_STRING,
    var zone: String = Constant.EMPTY_STRING,
    var section: String = Constant.EMPTY_STRING,
    var consultantCode: String = Constant.EMPTY_STRING,
    var isSubscribed: Boolean = false,
    var share: Boolean = false,
    var buy: Boolean = false,
    var sale: Float = Constant.ZERO_FLOAT,
    var orders: Int = Constant.NUMBER_ZERO,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG
) {

    @TargetIdProperty("consultantParentCode")
    var consultantParent: ToOne<ConsultantEntity> = ToOne(this, DigitalConsultantEntity_.consultantParent)

}
