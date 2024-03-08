package biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class BrightPathNextLevelSalesEntity(
    @Id
    var id: Long = Constant.NUMBER_ZERO_LONG,
    val name: String = Constant.EMPTY_STRING,
    val salesRequirement: Double = Constant.ZERO_DECIMAL,
    val average: Double = Constant.ZERO_DECIMAL,
) {
    @TargetIdProperty("brightPathNextLevelSalesParentCode")
    var brightPathNextLevelSalesParent: ToOne<BrightPathNextLevelEntity> =
        ToOne(
            this,
            BrightPathNextLevelSalesEntity_.brightPathNextLevelSalesParent
        )
}
