package biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne


@Entity
data class TopSalesCoCategoriesEntity(
    var name: String = EMPTY_STRING,
    var units: Int = NUMBER_ZERO,
    @Id var id: Long = NUMBER_ZERO.toLong()
) {

    @TargetIdProperty("salesConsultantsCategoriesParentCode")
    var salesConsultantsCategoriesParent: ToOne<TopSalesCoEntity> =
        ToOne(this, TopSalesCoCategoriesEntity_.salesConsultantsCategoriesParent)

    @Backlink(to = "salesConsultantCategoriesProductParent")
    var categoriesProducts: ToMany<TopSalesCoCategoriesProductsEntity> =
        ToMany(this, TopSalesCoCategoriesEntity_.categoriesProducts)

}
