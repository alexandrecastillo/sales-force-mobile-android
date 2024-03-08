package biz.belcorp.salesforce.core.entities.collection

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class ProfitEntity(
    var campaign: String = EMPTY_STRING,
    var profile: String = EMPTY_STRING,
    var region: String = EMPTY_STRING,
    var zone: String = EMPTY_STRING,
    var section: String = EMPTY_STRING,
    var total: Double = ZERO_DECIMAL,
    var competitionTotal: Double = ZERO_DECIMAL,
    var competitionCapitalization: Double = ZERO_DECIMAL,
    var competition6d6_low_value: Double = ZERO_DECIMAL,
    var competition6d6_high_value: Double = ZERO_DECIMAL,
    var competition6d6_total: Double = ZERO_DECIMAL,
    var competitionChangeLevel: Double = ZERO_DECIMAL,
    var competitionNewFixed: Double = ZERO_DECIMAL,
    var competitionProductsRelease: Double = ZERO_DECIMAL,
    var competition_tactic_bonus_level: String? = EMPTY_STRING,
    var competition_tactic_bonus_amount: Double = ZERO_DECIMAL,
    var ordersTotal: Double = ZERO_DECIMAL,
    var ordersPotential: Double = ZERO_DECIMAL,
    @Id
    var id: Long = NUMBER_ZERO.toLong()
) {

    @Backlink(to = "profitParent")
    var ordersRange: ToMany<ProfitOrderEntity> = ToMany(this, ProfitEntity_.ordersRange)
}
