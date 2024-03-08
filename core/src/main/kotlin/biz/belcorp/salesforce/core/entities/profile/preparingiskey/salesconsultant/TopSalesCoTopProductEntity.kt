package biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_DOUBLE
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import biz.belcorp.salesforce.core.constants.Constant.NUMERO_CERO
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salessection.TopSalesSeEntity
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
class TopSalesCoTopProductEntity(
    var top: Int = NUMERO_CERO,
    var name: String = EMPTY_STRING,
    var units: Double = EMPTY_DOUBLE,
    @Id var id: Long = NUMBER_ZERO_LONG
) {

    @TargetIdProperty("salesConsultantsTopProductsParentCode")
    var salesConsultantsTopProductsParent: ToOne<TopSalesCoEntity> =
        ToOne(this, TopSalesCoTopProductEntity_.salesConsultantsTopProductsParent)

    @TargetIdProperty("salesSectionTopProductsParentCode")
    var salesSectionTopProductsParent: ToOne<TopSalesSeEntity> =
        ToOne(this, TopSalesCoTopProductEntity_.salesSectionTopProductsParent)

    @Backlink(to = "salesConsultantTopProductHistoryParent")
    var topProductsHistory: ToMany<TopSalesConsultantTopProductHistoryEntity> =
        ToMany(this, TopSalesCoTopProductEntity_.topProductsHistory)

}
