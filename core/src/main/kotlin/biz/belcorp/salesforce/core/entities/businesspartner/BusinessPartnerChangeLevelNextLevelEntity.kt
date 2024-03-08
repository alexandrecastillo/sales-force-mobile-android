package biz.belcorp.salesforce.core.entities.businesspartner

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class BusinessPartnerChangeLevelNextLevelEntity(
    val name: String = Constant.EMPTY_STRING,
    val accomplished: Boolean = false,
    val campaigns_accomplished: Int = Constant.NUMBER_ZERO,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG
) {

    @TargetIdProperty("businessPartnerChangeLevelNextLevelParentCode")
    var businessPartnerChangeLevelNextLevelParent: ToOne<BusinessPartnerChangeLevelEntity> =
        ToOne(this, BusinessPartnerChangeLevelNextLevelEntity_.businessPartnerChangeLevelNextLevelParent)

    @Backlink(to = "businessPartnerChangeNextLevelSalesParent")
    var sales: ToMany<BusinessPartnerChangeSalesEntity> =
        ToMany(this, BusinessPartnerChangeLevelNextLevelEntity_.sales)

    @Backlink(to = "businessPartnerChangeNextLevelOrdersParent")
    var orders: ToMany<BusinessPartnerChangeOrdersEntity> =
        ToMany(this, BusinessPartnerChangeLevelNextLevelEntity_.orders)
}
