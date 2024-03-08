package biz.belcorp.salesforce.core.entities.businesspartner

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class BusinessPartnerChangeLevelEntity(
    val campaign: String = Constant.EMPTY_STRING,
    val profile: String = Constant.EMPTY_STRING,
    val region: String = Constant.EMPTY_STRING,
    val zone: String = Constant.EMPTY_STRING,
    val section: String = Constant.EMPTY_STRING,
    val consultantCode: String = Constant.EMPTY_STRING,
    val campaignRequirement: Int = Constant.NUMBER_ZERO,
    val sectionSales: Double = Constant.EMPTY_DOUBLE,
    val sectionOrders: Int = Constant.NUMBER_ZERO,
    val gainAmountLowValue: Double = Constant.EMPTY_DOUBLE,
    val gainAmountLowValuePlus: Double = Constant.EMPTY_DOUBLE,
    val gainAmountHighValue: Double = Constant.EMPTY_DOUBLE,
    val gainAmountHighValuePlus: Double = Constant.EMPTY_DOUBLE,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG,
) {

    @Backlink(to = "businessPartnerChangeLevelLevelParent")
    var level: ToMany<BusinessPartnerChangeLevelLevelEntity> =
        ToMany(this, BusinessPartnerChangeLevelEntity_.level)

    @Backlink(to = "businessPartnerChangeLevelNextLevelParent")
    var nextLevel: ToMany<BusinessPartnerChangeLevelNextLevelEntity> =
        ToMany(this, BusinessPartnerChangeLevelEntity_.nextLevel)

    @Backlink(to = "businessPartnerChangeLevelRequirementParent")
    var levelRequirement: ToMany<BusinessPartnerChangeLevelRequirementEntity> =
        ToMany(this, BusinessPartnerChangeLevelEntity_.levelRequirement)

}
