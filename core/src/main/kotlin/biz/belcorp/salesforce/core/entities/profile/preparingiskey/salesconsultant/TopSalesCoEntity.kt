package biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class TopSalesCoEntity(
    var campaign: String = EMPTY_STRING,
    var region: String = EMPTY_STRING,
    var zone: String = EMPTY_STRING,
    var section: String = EMPTY_STRING,
    var consultantCode: String = EMPTY_STRING,
    var topSellingBrand: String? = EMPTY_STRING,
    var brandToPromote: String? = EMPTY_STRING,
    @Id var id: Long = NUMBER_ZERO.toLong()
) {
    @Backlink(to = "salesConsultantsBrandCategoriesParent")
    var brandCategories: ToMany<TopSalesCoBrandCategoriesEntity> =
        ToMany(this, TopSalesCoEntity_.brandCategories)

    @Backlink(to = "salesConsultantsBrandsParent")
    var brands: ToMany<TopSalesCoBrandsEntity> =
        ToMany(this, TopSalesCoEntity_.brands)


    @Backlink(to = "salesConsultantsCategoriesParent")
    var categories: ToMany<TopSalesCoCategoriesEntity> =
        ToMany(this, TopSalesCoEntity_.categories)

    @Backlink(to = "salesConsultantsTopProductsParent")
    var topProducts: ToMany<TopSalesCoTopProductEntity> =
        ToMany(this, TopSalesCoEntity_.topProducts)


    @Backlink(to = "salesConsultantsMultiMarkParent")
    var multiMark: ToMany<TopSalesCoMultiMarkEntity> =
        ToMany(this, TopSalesCoEntity_.multiMark)


    @Backlink(to = "salesConsultantsMultiCategories")
    var multiCategories: ToMany<TopSalesCoMultiCategoryEntity> =
        ToMany(this, TopSalesCoEntity_.multiCategories)

    @Backlink(to = "salesConsultantsTrioLbel")
    var trioLbel: ToMany<TopSalesCoTriLbelEntity> =
        ToMany(this, TopSalesCoEntity_.trioLbel)

    @Backlink(to = "salesConsultantsEsikaCode")
    var esika: ToMany<TopSalesCoEsikaEntity> =
        ToMany(this, TopSalesCoEntity_.esika)

    @Backlink(to = "salesConsultantsTrioLbelStayCode")
    var trioLbelStay: ToMany<TopSalesCoTriLbelStayEntity> =
        ToMany(this, TopSalesCoEntity_.trioLbelStay)


}
