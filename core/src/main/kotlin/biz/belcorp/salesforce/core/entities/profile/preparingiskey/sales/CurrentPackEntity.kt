package biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class CurrentPackEntity(
    @Id
    var id: Long = NUMBER_ZERO_LONG,
    val campaign: String = EMPTY_STRING,
    val brandCode: String = EMPTY_STRING,
    val brand: String = EMPTY_STRING,
    val average: Double = Constant.ZERO_DECIMAL,
    val amount: Int = Constant.NUMBER_ZERO,
) {
    @TargetIdProperty("currentPackParentCode")
    var currentPackParent: ToOne<ConsultantSaleEntity> =
        ToOne(
            this,
            CurrentPackEntity_.currentPackParent
        )
}


