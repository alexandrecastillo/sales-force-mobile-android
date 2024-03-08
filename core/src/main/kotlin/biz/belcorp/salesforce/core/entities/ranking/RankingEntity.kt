package biz.belcorp.salesforce.core.entities.ranking

import biz.belcorp.salesforce.core.constants.Constant
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class RankingEntity(
    @Id
    var id: Long = Constant.NUMBER_ZERO_LONG,
    val campaign: String = Constant.EMPTY_STRING,
    val region: String = Constant.EMPTY_STRING,
    val zone: String = Constant.EMPTY_STRING,
    val productivityPercentage: Double = Constant.ZERO_DECIMAL,
    val uniqueIpPercentage: Double = Constant.ZERO_DECIMAL,
    val retention6d6Percentage: Double = Constant.ZERO_DECIMAL,
    val activesGoalRetentionPercentage: Double = Constant.ZERO_DECIMAL
)
