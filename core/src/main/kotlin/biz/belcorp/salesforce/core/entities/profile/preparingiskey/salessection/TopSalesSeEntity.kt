package biz.belcorp.salesforce.core.entities.profile.preparingiskey.salessection

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoBrandsEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTopProductEntity
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class TopSalesSeEntity(
    var campaign: String = EMPTY_STRING,
    var region: String = EMPTY_STRING,
    var zone: String = EMPTY_STRING,
    var section: String = EMPTY_STRING,
    @Id var id: Long = NUMBER_ZERO.toLong()
) {

    @Backlink(to = "salesSectionBrandsParent")
    var brands: ToMany<TopSalesCoBrandsEntity> =
        ToMany(this, TopSalesSeEntity_.brands)

    @Backlink(to = "salesSectionTopProductsParent")
    var topProducts: ToMany<TopSalesCoTopProductEntity> =
        ToMany(this, TopSalesSeEntity_.topProducts)

}
