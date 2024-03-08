package biz.belcorp.salesforce.core.entities.dream

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class DreamCampaignEntity(
    @Id
    var id: Long = Constant.NUMBER_ZERO.toLong(),
    var campaign: String? = Constant.EMPTY_STRING,
    var gainAmount: Float = Constant.ZERO_FLOAT,
){
    @TargetIdProperty("dreamCollectionParentCode")
    var dreamCollectionParent: ToOne<DreamEntity> = ToOne(this, DreamCampaignEntity_.dreamCollectionParent)
}
