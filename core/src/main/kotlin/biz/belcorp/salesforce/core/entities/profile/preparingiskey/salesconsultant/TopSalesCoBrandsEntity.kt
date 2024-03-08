package biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salessection.TopSalesSeEntity
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class TopSalesCoBrandsEntity(
    var name: String = EMPTY_STRING,
    var units: Int = NUMBER_ZERO,
    @Id var id: Long = NUMBER_ZERO.toLong()
) {

    @TargetIdProperty("salesConsultantsBrandsParentCode")
    var salesConsultantsBrandsParent: ToOne<TopSalesCoEntity> =
        ToOne(this, TopSalesCoBrandsEntity_.salesConsultantsBrandsParent)

    @TargetIdProperty("salesSectionBrandsParentCode")
    var salesSectionBrandsParent: ToOne<TopSalesSeEntity> =
        ToOne(this, TopSalesCoBrandsEntity_.salesSectionBrandsParent)

}
