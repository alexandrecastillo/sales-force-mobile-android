package biz.belcorp.salesforce.core.entities.businesspartner

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class BusinessPartnerChangeSalesEntity(
    val requirement: Int = Constant.NUMBER_ZERO,
    val real: Float = Constant.ZERO_FLOAT,
    val accomplished: Boolean = false,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG
) {

    @TargetIdProperty("businessPartnerChangeNextLevelSalesParentCode")
    var businessPartnerChangeNextLevelSalesParent: ToOne<BusinessPartnerChangeLevelNextLevelEntity> =
        ToOne(this, BusinessPartnerChangeSalesEntity_.businessPartnerChangeNextLevelSalesParent)
}
