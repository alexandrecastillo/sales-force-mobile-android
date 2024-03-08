package biz.belcorp.salesforce.core.entities.businesspartner

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class BusinessPartnerChangeLevelRequirementEntity(
    val level: String = Constant.EMPTY_STRING,
    val minimumSales: Int = Constant.NUMBER_ZERO,
    val minimumOrders: Int = Constant.NUMBER_ZERO,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG
) {

    @TargetIdProperty("businessPartnerChangeLevelRequirementParentCode")
    var businessPartnerChangeLevelRequirementParent: ToOne<BusinessPartnerChangeLevelEntity> =
        ToOne(this, BusinessPartnerChangeLevelRequirementEntity_.businessPartnerChangeLevelRequirementParent)
}
