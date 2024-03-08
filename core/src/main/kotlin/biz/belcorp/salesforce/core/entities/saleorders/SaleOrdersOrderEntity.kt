package biz.belcorp.salesforce.core.entities.saleorders

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class SaleOrdersOrderEntity(
    @Id
    var id: Long = NUMBER_ZERO.toLong(),
    var range: String = EMPTY_STRING,
    var amount: Int = NUMBER_ZERO,
    var position: Int = NUMBER_ZERO
) {
    @TargetIdProperty("saleOrdersOrderParentCode")
    var saleOrdersOrdersParent: ToOne<SaleOrdersEntity> =
        ToOne(this, SaleOrdersOrderEntity_.saleOrdersOrdersParent)
}
