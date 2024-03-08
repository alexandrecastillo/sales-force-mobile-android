package biz.belcorp.salesforce.core.entities.campaignprojection

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class CampaignProjectionEntity(
    @Id
    var id: Long = Constant.NUMBER_ZERO.toLong(),
    val region: String? = Constant.EMPTY_STRING,
    val zone: String? = Constant.EMPTY_STRING,
    val section: String? = Constant.EMPTY_STRING,
    val dateCreated: String? = Constant.EMPTY_STRING,
    val dateEdited: String? = Constant.EMPTY_STRING,
    val campaign: String? = Constant.EMPTY_STRING,
    val pegsInMySection: Int? = Constant.NUMBER_ZERO,
    val pegsRetentionExpected: Int? = Constant.NUMBER_ZERO,
    val pegsRetentionReal: Int? = Constant.NUMBER_ZERO,
    val initialActives: Int? = Constant.NUMBER_ZERO,
    val finalProjectedActives: Int? = Constant.NUMBER_ZERO,
    val projectedCapitalization: Int? = Constant.NUMBER_ZERO,
    val entriesProjected: Int? = Constant.NUMBER_ZERO,
    val entriesReal: Int? = Constant.NUMBER_ZERO,
    val reEntriesProjected: Int? = Constant.NUMBER_ZERO,
    val reEntriesReal: Int? = Constant.NUMBER_ZERO,
    val finalsLastYearActives: Int? = Constant.NUMBER_ZERO,
    val ordersExpectedTotal: Int? = Constant.NUMBER_ZERO,
    val currentTotalOrders: Int? = Constant.NUMBER_ZERO,
    val ordersTotalGain: Float? = Constant.ZERO_FLOAT,
    val reEntriesLast5C: Int? = Constant.NUMBER_ZERO,
    val retentionGain6d6Low: Float? = Constant.ZERO_FLOAT,
    val retentionGain6d6High: Float? = Constant.ZERO_FLOAT,
    val retentionGain6d6HighMultiBrand: Float? = Constant.ZERO_FLOAT,
    val capiGainByPointProjectionCurrent: Int? = Constant.NUMBER_ZERO,
    val capiGainByPointProjectionReal: Int? = Constant.NUMBER_ZERO,
    val capitalizationCurrent: Int? = Constant.NUMBER_ZERO,
    val capitalizationReal: Int? = Constant.NUMBER_ZERO,
    val gainCurrent: Int? = Constant.NUMBER_ZERO,
    val gainReal: Int? = Constant.NUMBER_ZERO,
) {
    @Backlink(to = "ordersCollectionParent")
    var ordersList: ToMany<CampaignProjectionOrderEntity> =
        ToMany(this, CampaignProjectionEntity_.ordersList)

    @Backlink(to = "campaignProjectionParent")
    var retentionList: ToMany<CampaignProjectionRetentionEntity> =
        ToMany(this, CampaignProjectionEntity_.retentionList)


}
