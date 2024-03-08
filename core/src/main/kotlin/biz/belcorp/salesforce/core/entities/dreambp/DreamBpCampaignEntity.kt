package biz.belcorp.salesforce.core.entities.dreambp

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne


@Entity
data class DreamBpCampaignEntity(
    @Id
    var id: Long = Constant.NUMBER_ZERO.toLong(),
    var campaign: String? = Constant.EMPTY_STRING,
    // this field remains with name gainAmount to avoid troubles with DB
    var gainAmount: Float = Constant.ZERO_FLOAT,
) {
    @TargetIdProperty("dreamBpCollectionParentCode")
    var dreamBpCollectionParent: ToOne<DreamBpEntity> = ToOne(
        this,
        DreamBpCampaignEntity_.dreamBpCollectionParent
    )
}
