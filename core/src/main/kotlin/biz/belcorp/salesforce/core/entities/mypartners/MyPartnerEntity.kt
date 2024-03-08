package biz.belcorp.salesforce.core.entities.mypartners

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class MyPartnerEntity(
    val campaign: String = Constant.EMPTY_STRING,
    val region: String = Constant.EMPTY_STRING,
    val zone: String = Constant.EMPTY_STRING,
    val section: String = Constant.EMPTY_STRING,
    val consultantId: Int = Constant.NUMBER_ZERO,
    val consultantCode: String = Constant.EMPTY_STRING,
    val anniversaryDate: String = Constant.EMPTY_STRING,
    val admissionCampaign: String = Constant.EMPTY_STRING,
    val pendingDebt: Double = Constant.EMPTY_DOUBLE,
    val productivity: String = Constant.EMPTY_STRING,
    val isSuccessful: Boolean = false,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG,
) {

    @Backlink(to = "myPartnersLevelParent")
    var level: ToMany<MyPartnerLevelEntity> =
        ToMany(this, MyPartnerEntity_.level)

    @Backlink(to = "myPartnerNextLevelParent")
    var nextLevel: ToMany<MyPartnerNextLevelEntity> =
        ToMany(this, MyPartnerEntity_.nextLevel)

    @Backlink(to = "myPartnerProfileInfoParent")
    var personalInfo: ToMany<MyPartnerPersonalInfoEntity> =
        ToMany(this, MyPartnerEntity_.personalInfo)

    @Backlink(to = "myPartnerBillingInfoParent")
    var billingInfo: ToMany<MyPartnerBillingInfoEntity> =
        ToMany(this, MyPartnerEntity_.billingInfo)

    @Backlink(to = "myPartnerIsSuccessfulHistoryParent")
    var successfulHistory: ToMany<MyPartnerSuccessfulHistoryEntity> =
        ToMany(this, MyPartnerEntity_.successfulHistory)

}
