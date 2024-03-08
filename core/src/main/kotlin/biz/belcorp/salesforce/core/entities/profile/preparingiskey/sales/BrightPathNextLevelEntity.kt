package biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class BrightPathNextLevelEntity(
    @Id
    var id: Long = Constant.NUMBER_ZERO_LONG,
    val name: String = Constant.EMPTY_STRING
) {
    @TargetIdProperty("brightPathNextLevelParentCode")
    var brightPathNextLevelParent: ToOne<ConsultantSaleEntity> =
        ToOne(
            this,
            BrightPathNextLevelEntity_.brightPathNextLevelParent
        )

    @Backlink(to = "brightPathNextLevelSalesParent")
    var brightPathNextLevelSales: ToMany<BrightPathNextLevelSalesEntity> =
        ToMany(this, BrightPathNextLevelEntity_.brightPathNextLevelSales)

    @Backlink(to = "brightPathNextLevelOrderParent")
    var brightPathNextLevelOrder: ToMany<BrightPathNextLevelOrderEntity> =
        ToMany(this, BrightPathNextLevelEntity_.brightPathNextLevelOrder)
}
