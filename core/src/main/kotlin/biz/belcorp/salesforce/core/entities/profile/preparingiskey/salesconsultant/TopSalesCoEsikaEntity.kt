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
class TopSalesCoEsikaEntity(
    val order: Int = NUMBER_ZERO,
    val name: String = EMPTY_STRING,
    val sku: String = EMPTY_STRING,
    val cuv: String = EMPTY_STRING,
    @Id var id: Long = NUMBER_ZERO_LONG
) {

    @TargetIdProperty("salesConsultantsEsikaCode")
    var salesConsultantsEsika: ToOne<TopSalesCoEntity> =
        ToOne(this, TopSalesCoEsikaEntity_.salesConsultantsEsika)

    @Backlink(to = "salesConsultantsEsikaHistoryParent")
    var historyEsika: ToMany<TopSalesCoEsikaHistoryEntity> =
        ToMany(this, TopSalesCoEsikaEntity_.historyEsika)

    @Backlink(to = "salesConsultantsEsikaImagesParent")
    var imagesProductParent: ToMany<TopSalesCoEsikaImageEntity> =
        ToMany(this, TopSalesCoEsikaEntity_.imagesProductParent)

}
