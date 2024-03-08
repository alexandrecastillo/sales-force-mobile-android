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
class TopSalesCoTriLbelEntity(
    val order: Int = NUMBER_ZERO,
    val name: String = EMPTY_STRING,
    val sku: String = EMPTY_STRING,
    val cuv: String = EMPTY_STRING,
    @Id var id: Long = NUMBER_ZERO_LONG
) {

    @TargetIdProperty("salesConsultantsTrioLbelCode")
    var salesConsultantsTrioLbel: ToOne<TopSalesCoEntity> =
        ToOne(this, TopSalesCoTriLbelEntity_.salesConsultantsTrioLbel)

    @Backlink(to = "salesConsultantsTrioLbelHistoryParent")
    var historyTrioLbel: ToMany<TopSalesCoTrioLbelHistoryEntity> =
        ToMany(this, TopSalesCoTriLbelEntity_.historyTrioLbel)

    @Backlink(to = "salesConsultantsTrioLbelImagesParent")
    var imagesProductParent: ToMany<TopSalesCoTrioLbelImageEntity> =
        ToMany(this, TopSalesCoTriLbelEntity_.imagesProductParent)
}
