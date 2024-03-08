package biz.belcorp.salesforce.core.entities.mypartners

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class MyPartnerSuccessfulHistoryEntity(
    val campaign: String = Constant.EMPTY_STRING,
    val value: Boolean = false,
    @Id var id: Long = Constant.NUMBER_ZERO_LONG
) {

    @TargetIdProperty("myPartnerIsSuccessfulHistoryParent")
    var successfulHistory: ToOne<MyPartnerEntity> =
        ToOne(this, MyPartnerSuccessfulHistoryEntity_.successfulHistory)
}
