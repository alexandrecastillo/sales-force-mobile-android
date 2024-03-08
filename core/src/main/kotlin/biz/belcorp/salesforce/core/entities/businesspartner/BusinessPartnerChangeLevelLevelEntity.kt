package biz.belcorp.salesforce.core.entities.businesspartner

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class BusinessPartnerChangeLevelLevelEntity(
    val code: String = Constant.EMPTY_STRING,
    val name: String = Constant.EMPTY_STRING,
    val consecutiveCampaigns: Int = Constant.NUMBER_ZERO,
    val campaignsNotAccomplished: Int = Constant.NUMBER_ZERO,
    val accomplished: Boolean = false,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG
) {

    @TargetIdProperty("businessPartnerChangeLevelLevelParentCode")
    var businessPartnerChangeLevelLevelParent: ToOne<BusinessPartnerChangeLevelEntity> =
        ToOne(this, BusinessPartnerChangeLevelLevelEntity_.businessPartnerChangeLevelLevelParent)
}
