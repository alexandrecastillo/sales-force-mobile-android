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
data class TopSalesCoBrandCategoriesEntity(
    var brand: String = EMPTY_STRING,
    var units: Int = NUMBER_ZERO,
    var categoryOrder: Int = NUMBER_ZERO,
    var categoryUnits: Int = NUMBER_ZERO,
    var categoryName: String = EMPTY_STRING,
    @Id var id: Long = NUMBER_ZERO.toLong()
) {
    @TargetIdProperty("salesConsultantsBrandCategoriesParentCode")
    var salesConsultantsBrandCategoriesParent: ToOne<TopSalesCoEntity> =
        ToOne(
            this,
            TopSalesCoBrandCategoriesEntity_.salesConsultantsBrandCategoriesParent
        )

    @Backlink(to = "salesConsultantsBrandCategoriesCategoryParent")
    var categories: ToMany<TopSalesCoBrandCategoriesCategoryEntity> =
        ToMany(this, TopSalesCoBrandCategoriesEntity_.categories)




}
