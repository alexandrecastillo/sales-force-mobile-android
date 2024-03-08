package biz.belcorp.salesforce.core.entities.dream

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class DreamEntity(
    @Id
    var id: Long = Constant.NUMBER_ZERO.toLong(),
    var dreamId: String = Constant.EMPTY_STRING,
    var consultantCode: String? = Constant.EMPTY_STRING,
    val amountToComplete: Int = Constant.NUMBER_ZERO,
    val numberCampaignsToComplete: Int = Constant.NUMBER_ZERO,
    val dream: String? = Constant.EMPTY_STRING,
    val region: String? = Constant.EMPTY_STRING,
    val zone: String? = Constant.EMPTY_STRING,
    val section: String? = Constant.EMPTY_STRING,
    val comment: String? = Constant.EMPTY_STRING,
    val campaignCreated: String? = Constant.EMPTY_STRING,
    val status: String? = Constant.EMPTY_STRING,
    val campaignEnd: String? = Constant.EMPTY_STRING,
    val dateCreated: String? = Constant.EMPTY_STRING,
    val dateEdited: String? = Constant.EMPTY_STRING,
    val dateCompleted: String? = Constant.EMPTY_STRING,
    val totalGain: Float = Constant.ZERO_FLOAT,
){
    @Backlink(to = "dreamCollectionParent")
    var campaignList: ToMany<DreamCampaignEntity> = ToMany(this, DreamEntity_.campaignList)

}
