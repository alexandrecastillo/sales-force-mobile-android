package biz.belcorp.salesforce.core.entities.rejectedorders

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class RejectedOrderDetailEntity(
    @Id(assignable = false)
    var code: Long = Constant.NUMERO_CERO.toLong(),
    var name: String = Constant.EMPTY_STRING,
    var quantity: Int = Constant.NUMERO_CERO
) {
    @TargetIdProperty("rejectedOrderParentCode")
    var rejectedOrderParent: ToOne<RejectedOrderEntity> =
        ToOne(this, RejectedOrderDetailEntity_.rejectedOrderParent)
}
