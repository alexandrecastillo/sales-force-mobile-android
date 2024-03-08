package biz.belcorp.salesforce.core.entities.rejectedorders

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class RejectedOrderEntity(
    @Id(assignable = false) var code: Long = Constant.NUMERO_CERO.toLong(),
    var campaign: String = Constant.EMPTY_STRING,
    var region: String = Constant.EMPTY_STRING,
    var zone: String = Constant.EMPTY_STRING,
    var section: String = Constant.EMPTY_STRING
) {
    @Backlink(to = "rejectedOrderParent")
    var reasons: ToMany<RejectedOrderDetailEntity> = ToMany(this, RejectedOrderEntity_.reasons)
}
