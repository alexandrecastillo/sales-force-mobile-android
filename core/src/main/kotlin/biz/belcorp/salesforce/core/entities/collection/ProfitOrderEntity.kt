package biz.belcorp.salesforce.core.entities.collection

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class ProfitOrderEntity(
    @Id
    var id: Long = NUMBER_ZERO.toLong(),
    val range: String = EMPTY_STRING,
    val amount: Double = ZERO_DECIMAL,
    val position: Int = NUMBER_ZERO
) {

    @TargetIdProperty("profitParentCode")
    var profitParent: ToOne<ProfitEntity> = ToOne(this, ProfitOrderEntity_.profitParent)

}
