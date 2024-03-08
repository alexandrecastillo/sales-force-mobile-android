package biz.belcorp.salesforce.core.entities.businesspartner

import biz.belcorp.salesforce.core.entities.SuccessfuHistoricEntity
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
class BusinessPartnerEntity(
    @Id var code: Long = 0,
    val region: String,
    val zone: String,
    val section: String,
    val partnerCode: String,
    val anniversaryDate: String,
    val campaignAdmission: String,
    val document: String,
    val name: String,
    val firstName: String,
    val secondName: String,
    val firstSurname: String,
    val secondSurname: String,
    val email: String,
    val address: String,
    val cellphone: String,
    val homephone: String,
    val birthDate: String,
    val levelCode: String,
    val level: String,
    val pendingDebt: Double,
    val productivity: String,
    val billingFirstCampaign: String,
    val billingLastCampaign: String,
    val status: String,
    val leaderClassification: String,
    val successful: Boolean
) {

    @Backlink
    var successfuHistoric: ToMany<SuccessfuHistoricEntity> =
        ToMany(this, BusinessPartnerEntity_.successfuHistoric)

}
