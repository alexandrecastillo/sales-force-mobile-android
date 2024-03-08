package biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class TopSalesCoCategoriesProductsBuyCampaignEntity(
    var campaign1: String = Constant.EMPTY_STRING,
    var campaign2: String = Constant.EMPTY_STRING,
    var campaign3: String = Constant.EMPTY_STRING,
    @Id var id: Long = Constant.NUMBER_ZERO.toLong()
){

    @TargetIdProperty("salesConsultantsCategoriesProductsBuyCampaignsParentCode")
    var topSalesCoCategoriesProductsBuyCampaignParent: ToOne<TopSalesCoCategoriesProductsEntity> =
        ToOne(
            this,
            TopSalesCoCategoriesProductsBuyCampaignEntity_.topSalesCoCategoriesProductsBuyCampaignParent
        )
}
