package biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne


@Entity
class TopSalesMultiMarkHistoryEntity(
    val campaign: String = EMPTY_STRING,
    val multimarca: Int = NUMBER_ZERO,
    @Id var id: Long = NUMBER_ZERO.toLong()
) {

    @TargetIdProperty("salesConsultantsMultiMarkHistoryParentCode")
    var salesConsultantsMultiMarkHistoryParent: ToOne<TopSalesCoMultiMarkEntity> =
        ToOne(
            this,
            TopSalesMultiMarkHistoryEntity_.salesConsultantsMultiMarkHistoryParent
        )
}
