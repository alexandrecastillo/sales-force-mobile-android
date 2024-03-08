package biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class BrightPathLevelEntity(
    @Id
    var id: Long = NUMBER_ZERO_LONG,
    val name: String = EMPTY_STRING,
    val accumulativeSales: Double = ZERO_DECIMAL,
    val accumulativeOrders: String? = EMPTY_STRING,
    val hasOrder: Boolean = false,
    val salesReal: Double? = ZERO_DECIMAL,
    val salesAverage: Double = ZERO_DECIMAL,
    val salesToRetainLevel: Double = ZERO_DECIMAL,
    val currentLevelSalesRequirement: Double = ZERO_DECIMAL,
    val currentLevelOrderRequirement: Int = Constant.NUMBER_ZERO,
    val campaignProgress: Int = Constant.NUMBER_ZERO,
    val averageCurrentLevel: Double? = ZERO_DECIMAL,
) {
    @TargetIdProperty("brightPathLevelParentCode")
    var brightPathLevelParent: ToOne<ConsultantSaleEntity> =
        ToOne(
            this,
            BrightPathLevelEntity_.brightPathLevelParent
        )
}


