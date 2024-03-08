package biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class BrightPathNextLevelOrderEntity(
    @Id
    var id: Long = Constant.NUMBER_ZERO_LONG,
    val requirement: Int = Constant.NUMBER_ZERO
) {
    @TargetIdProperty("brightPathNextLevelOrderParentCode")
    var brightPathNextLevelOrderParent: ToOne<BrightPathNextLevelEntity> =
        ToOne(
            this,
            BrightPathNextLevelOrderEntity_.brightPathNextLevelOrderParent
        )
}
