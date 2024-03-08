package biz.belcorp.salesforce.core.entities.campaignprojection

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne


@Entity
data class CampaignProjectionOrderEntity(
    @Id
    var id: Long = Constant.NUMBER_ZERO.toLong(),
    val title: String? = Constant.EMPTY_STRING,
    val unitsExpected: Int? = Constant.NUMBER_ZERO,
    val unitsReal: Int? = Constant.NUMBER_ZERO,
    val gainPerOrder: Float? = Constant.ZERO_FLOAT,
    val gainPerOrderNotSuccess: Float? = Constant.ZERO_FLOAT,
    val gainPerOrderExpected: Float? = Constant.ZERO_FLOAT,
) {
    @TargetIdProperty("ordersCollectionParentCode")
    var ordersCollectionParent: ToOne<CampaignProjectionEntity> =
        ToOne(this, CampaignProjectionOrderEntity_.ordersCollectionParent)
}
