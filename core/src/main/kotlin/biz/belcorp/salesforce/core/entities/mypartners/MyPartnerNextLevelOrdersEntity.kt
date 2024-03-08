package biz.belcorp.salesforce.core.entities.mypartners

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class MyPartnerNextLevelOrdersEntity(
    val requirement: Int = Constant.NUMBER_ZERO,
    val real: Int = Constant.NUMBER_ZERO,
    val accomplished: Boolean = false,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG
) {

    @TargetIdProperty("myPartnerNextLevelOrdersParent")
    var nextLevelOrdersParent: ToOne<MyPartnerNextLevelEntity> =
        ToOne(this, MyPartnerNextLevelOrdersEntity_.nextLevelOrdersParent)
}
