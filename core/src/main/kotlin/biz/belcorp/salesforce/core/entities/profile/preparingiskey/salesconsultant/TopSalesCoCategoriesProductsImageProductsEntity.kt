package biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class TopSalesCoCategoriesProductsImageProductsEntity(
    var codsap: String = Constant.EMPTY_STRING,
    var photoProduct: List<String> = emptyList(),
    @Id var id: Long = Constant.NUMBER_ZERO.toLong()
) {

    @TargetIdProperty("salesConsultantsCategoriesProductsImagesParentCode")
    var salesConsultantsCategoriesProductsImagesParent: ToOne<TopSalesCoCategoriesProductsEntity> =
        ToOne(
            this,
            TopSalesCoCategoriesProductsImageProductsEntity_.salesConsultantsCategoriesProductsImagesParent
        )
}
