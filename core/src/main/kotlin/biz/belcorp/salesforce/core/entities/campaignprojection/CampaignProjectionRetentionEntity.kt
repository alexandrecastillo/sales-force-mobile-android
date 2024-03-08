package biz.belcorp.salesforce.core.entities.campaignprojection

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class CampaignProjectionRetentionEntity(
    @Id
    var id: Long = Constant.NUMBER_ZERO.toLong(),
    var title: String? = Constant.EMPTY_STRING,
    val activeConsultant: Int? = Constant.NUMBER_ZERO,
    val retentionExpected: Int? = Constant.NUMBER_ZERO,
    val retentionReal: Int? = Constant.NUMBER_ZERO
) {
    @TargetIdProperty("campaignProjectionParentCode")
    var campaignProjectionParent: ToOne<CampaignProjectionEntity> =
        ToOne(this, CampaignProjectionRetentionEntity_.campaignProjectionParent)
}
