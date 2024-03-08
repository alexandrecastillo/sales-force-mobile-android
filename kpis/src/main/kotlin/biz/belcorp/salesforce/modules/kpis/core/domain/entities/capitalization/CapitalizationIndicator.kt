package biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_DOUBLE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL

data class CapitalizationIndicator(
    val campaign: String = EMPTY_STRING,
    val region: String = EMPTY_STRING,
    val zone: String = EMPTY_STRING,
    val section: String = EMPTY_STRING,
    val capitalizationReal: Int = NUMBER_ZERO,
    val capitalizationGoal: Int = NUMBER_ZERO,
    val capitalizationProjected: Int = NUMBER_ZERO,
    val capitalizationEntries: Int = NUMBER_ZERO,
    val capitalizationEntriesGoal: Int = NUMBER_ZERO,
    val capitalizationReentries: Int = NUMBER_ZERO,
    val capitalizationExpenses: Int = NUMBER_ZERO,
    val capitalizationFulfillment: Double = ZERO_DECIMAL,
    val pegsReal: Int = NUMBER_ZERO,
    val pegRetentionGoal: Int = NUMBER_ZERO,
    val pegRetentionReal: Int = NUMBER_ZERO,
    val pegRetentionPercentage: Double = ZERO_DECIMAL,
    val pegRetentionRemaining: Int = NUMBER_ZERO,
    val potentialTotal: Int = NUMBER_ZERO,
    val potentialEntries: Int = NUMBER_ZERO,
    val potentialReentries: Int = NUMBER_ZERO,
    val capitalizationProactive: Int = NUMBER_ZERO,
    val capitalizationSuccess: Int = NUMBER_ZERO
)
