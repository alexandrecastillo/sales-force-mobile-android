package biz.belcorp.salesforce.core.entities.capitalization

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_DOUBLE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class CapitalizationEntity(
    var campaign: String = EMPTY_STRING,
    var region: String = EMPTY_STRING,
    var zone: String = EMPTY_STRING,
    var section: String = EMPTY_STRING,
    var profile: String = EMPTY_STRING,
    var capitalizationReal: Int = NUMBER_ZERO,
    var capitalizationGoal: Int = NUMBER_ZERO,
    var capitalizationProjected: Int = NUMBER_ZERO,
    var capitalizationEntries: Int = NUMBER_ZERO,
    var capitalizationEntriesGoal: Int = NUMBER_ZERO,
    var capitalizationReentries: Int = NUMBER_ZERO,
    var capitalizationExpenses: Int = NUMBER_ZERO,
    var capitalizationProactive: Int = NUMBER_ZERO,
    var capitalizationSuccess: Int = NUMBER_ZERO,
    var capitalizationFulfillment: Double = ZERO_DECIMAL,
    var pegsReal: Int = NUMBER_ZERO,
    var pegRetentionGoal: Int = NUMBER_ZERO,
    var pegRetentionReal: Int = NUMBER_ZERO,
    var pegRetentionPercentage: Double = ZERO_DECIMAL,
    var pegRetentionRemaining: Int = NUMBER_ZERO,
    var potentialTotal: Int = NUMBER_ZERO,
    var potentialEntries: Int = NUMBER_ZERO,
    var potentialReentries: Int = NUMBER_ZERO,
    @Id
    var id: Long = NUMBER_ZERO.toLong()
)
