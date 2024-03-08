package biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class ConsultantSaleEntity(
    @Id
    var id: Long = NUMBER_ZERO_LONG,
    val campaign: String = EMPTY_STRING,
    val region: String = EMPTY_STRING,
    val zone: String = EMPTY_STRING,
    val section: String = EMPTY_STRING,
    val consultantCode: String = EMPTY_STRING,
    val netSale: Double = ZERO_DECIMAL,
    val catalogSale: Double = ZERO_DECIMAL,
    val billedOrderAmount: Double = ZERO_DECIMAL,
    val isHighOrderValue: Boolean = false,
    val averageSaleLastSixCampaigns: Double = ZERO_DECIMAL,
    val gainAmount: Double = ZERO_DECIMAL,
    val constantU6c: Boolean = false,
    val brightPathPeriod_: String = EMPTY_STRING,
) {

    @Backlink(to = "brightPeriodParent")
    var brightPeriod: ToMany<BrightPeriodEntity> =
        ToMany(this, ConsultantSaleEntity_.brightPeriod)

    @Backlink(to = "currentPackParent")
    var currentPack: ToMany<CurrentPackEntity> =
        ToMany(this, ConsultantSaleEntity_.currentPack)

    @Backlink(to = "brightPathLevelParent")
    var brightPathLevel: ToMany<BrightPathLevelEntity> =
        ToMany(this, ConsultantSaleEntity_.brightPathLevel)

    @Backlink(to = "brightPathNextLevelParent")
    var brightPathNextLevel: ToMany<BrightPathNextLevelEntity> =
        ToMany(this, ConsultantSaleEntity_.brightPathNextLevel)
}


