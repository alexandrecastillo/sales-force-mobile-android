package biz.belcorp.salesforce.core.entities.mypartners

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class MyPartnerNextLevelEntity(
    val name: String = Constant.EMPTY_STRING,
    val accomplished: Boolean = false,
    val campaignsAccomplished: Int = Constant.NUMBER_ZERO,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG
) {

    @TargetIdProperty("myPartnerNextLevelParent")
    var nextLevelParent: ToOne<MyPartnerEntity> =
        ToOne(this, MyPartnerNextLevelEntity_.nextLevelParent)

    @Backlink(to = "myPartnerNextLevelSalesParent")
    var sales: ToMany<MyPartnerNextLevelSalesEntity> =
        ToMany(this, MyPartnerNextLevelEntity_.sales)

    @Backlink(to = "myPartnerNextLevelOrdersParent")
    var orders: ToMany<MyPartnerNextLevelOrdersEntity> =
        ToMany(this, MyPartnerNextLevelEntity_.orders)
}
