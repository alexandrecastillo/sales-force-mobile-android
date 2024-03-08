package biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class BrightPeriodEntity(
    @Id
    var id: Long = NUMBER_ZERO_LONG,
    val orders: Int = Constant.NUMBER_ZERO,
    val sale: Double = Constant.ZERO_DECIMAL
) {
    @TargetIdProperty("brightPeriodParentCode")
    var brightPeriodParent: ToOne<ConsultantSaleEntity> =
        ToOne(
            this,
            BrightPeriodEntity_.brightPeriodParent
        )
}


