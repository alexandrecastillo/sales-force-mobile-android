package biz.belcorp.salesforce.core.entities.mypartners

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class MyPartnerNextLevelSalesEntity(
    val requirement: Int = Constant.NUMBER_ZERO,
    val real: Double = Constant.ZERO_DECIMAL,
    val accomplished: Boolean = false,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG
) {

    @TargetIdProperty("myPartnerNextLevelSalesParent")
    var nextLevelSalesParent: ToOne<MyPartnerNextLevelEntity> =
        ToOne(this, MyPartnerNextLevelSalesEntity_.nextLevelSalesParent)
}
