package biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL

data class ProfitIndicator(
    val campaign: String = EMPTY_STRING,
    val profile: String = EMPTY_STRING,
    val region: String = EMPTY_STRING,
    val zone: String = EMPTY_STRING,
    val section: String = EMPTY_STRING,
    val total: Double = ZERO_DECIMAL,
    val competitionTotal: Double = ZERO_DECIMAL,
    val competitionCapitalization: Double = ZERO_DECIMAL,
    val competition6d6LowValue: Double = ZERO_DECIMAL,
    val competition6d6HighValue: Double = ZERO_DECIMAL,
    val competition6d6Total: Double = ZERO_DECIMAL,
    val competitionChangeLevel: Double = ZERO_DECIMAL,
    val competitionNewFixed: Double = ZERO_DECIMAL,
    val competitionProductsRelease: Double = ZERO_DECIMAL,
    val competitionTacticBonusLevel: String? = EMPTY_STRING,
    val competitionTacticBonusAmount: Double = ZERO_DECIMAL,
    val ordersTotal: Double = ZERO_DECIMAL,
    val ordersPotential: Double = ZERO_DECIMAL,
    val ordersRange: List<ProfitOrderRange> = emptyList()
)
