package biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_DOUBLE
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne


@Entity
class TopSalesCoCategoriesProductsEntity(
    var order: Int = NUMBER_ZERO,
    var name: String = EMPTY_STRING,
    var sku: String = EMPTY_STRING,
    var cuv: String = EMPTY_STRING,
    var quantity: Int = NUMBER_ZERO,
    var amount: Double = EMPTY_DOUBLE,
    @Id var id: Long = NUMBER_ZERO.toLong()
) {

    @TargetIdProperty("salesConsultantCategoriesProductParentCode")
    var salesConsultantCategoriesProductParent: ToOne<TopSalesCoCategoriesEntity> =
        ToOne(
            this,
            TopSalesCoCategoriesProductsEntity_.salesConsultantCategoriesProductParent
        )

    @Backlink(to = "salesConsultantsCategoriesProductsBuyCampaignsParentCode")
    var buyCampaignsProductParent: ToMany<TopSalesCoCategoriesProductsBuyCampaignEntity> =
        ToMany(this, TopSalesCoCategoriesProductsEntity_.buyCampaignsProductParent)

    @Backlink(to = "salesConsultantsCategoriesProductsImagesParent")
    var imagesProductParent: ToMany<TopSalesCoCategoriesProductsImageProductsEntity> =
        ToMany(this, TopSalesCoCategoriesProductsEntity_.imagesProductParent)
}
