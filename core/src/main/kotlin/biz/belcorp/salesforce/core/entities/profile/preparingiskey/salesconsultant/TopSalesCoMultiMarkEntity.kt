package biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
class TopSalesCoMultiMarkEntity(
    val order: Int = NUMBER_ZERO,
    val name: String = EMPTY_STRING,
    @Id var id: Long = NUMBER_ZERO_LONG
) {

    @TargetIdProperty("salesConsultantsMultiMarkParentCode")
    var salesConsultantsMultiMarkParent: ToOne<TopSalesCoEntity> =
        ToOne(this, TopSalesCoMultiMarkEntity_.salesConsultantsMultiMarkParent)

    @Backlink(to = "salesConsultantsMultiMarkHistoryParent")
    var historyMultiMark: ToMany<TopSalesMultiMarkHistoryEntity> =
        ToMany(this, TopSalesCoMultiMarkEntity_.historyMultiMark)
}
