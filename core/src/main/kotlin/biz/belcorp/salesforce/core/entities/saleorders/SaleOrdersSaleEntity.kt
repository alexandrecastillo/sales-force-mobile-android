package biz.belcorp.salesforce.core.entities.saleorders

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class SaleOrdersSaleEntity(
    @Id
    var id: Long = NUMBER_ZERO.toLong(),
    var range: String = EMPTY_STRING,
    var amount: Double = ZERO_DECIMAL,
    var position: Int = NUMBER_ZERO
) {

    @TargetIdProperty("saleOrdersSaleParentCode")
    var saleOrdersSalesParent: ToOne<SaleOrdersEntity> =
        ToOne(this, SaleOrdersSaleEntity_.saleOrdersSalesParent)
}
