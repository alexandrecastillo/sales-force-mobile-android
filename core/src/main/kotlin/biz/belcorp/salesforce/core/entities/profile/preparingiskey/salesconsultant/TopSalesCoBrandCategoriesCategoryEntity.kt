package biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class TopSalesCoBrandCategoriesCategoryEntity(
    @Id var id: Long = Constant.NUMBER_ZERO.toLong(),
    var order: Int = Constant.NUMBER_ZERO,
    var name: String = Constant.EMPTY_STRING,
    var units: Int = Constant.NUMBER_ZERO){

    @TargetIdProperty("salesConsultantsBrandCategoriesCategoryParentCode")
    var salesConsultantsBrandCategoriesCategoryParent: ToOne<TopSalesCoBrandCategoriesEntity> =
        ToOne(
            this,
            TopSalesCoBrandCategoriesCategoryEntity_.salesConsultantsBrandCategoriesCategoryParent
        )



}
