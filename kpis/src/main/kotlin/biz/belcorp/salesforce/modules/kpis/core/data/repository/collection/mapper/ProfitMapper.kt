package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.mapper

import biz.belcorp.salesforce.core.entities.collection.ProfitEntity
import biz.belcorp.salesforce.core.entities.collection.ProfitOrderEntity
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.ProfitDto
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.ProfitIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.ProfitOrderRange

class ProfitMapper {

    fun map(data: ProfitDto): List<ProfitEntity> {
        val profits = arrayListOf<ProfitEntity>()

        data.kpiGain.forEach {
            val profit = map(it)
            val profitRanges = it.orders.ranges.map { range -> map(range) }
            profit.ordersRange.addAll(profitRanges)
            profits.add(profit)
        }
        return profits
    }

    private fun map(data: ProfitDto.KpiGain): ProfitEntity = with(data) {
        return ProfitEntity(
            campaign = campaign,
            region = region.orEmpty(),
            zone = zone.orEmpty(),
            section = section.orEmpty(),
            total = total,
            competitionTotal = competition.total,
            competitionCapitalization = competition.capitalization,
            competition6d6_low_value = competition._6d6.lowValue,
            competition6d6_high_value = competition._6d6.highValue,
            competition6d6_total = competition._6d6.total,
            competitionChangeLevel = competition.changeLevel,
            competitionNewFixed = competition.newFixed,
            competitionProductsRelease = competition.productsRelease,
            competition_tactic_bonus_level = competition.tacticBonus.level,
            competition_tactic_bonus_amount = competition.tacticBonus.amount,
            ordersTotal = orders.total,
            ordersPotential = orders.potential
        )
    }

    private fun map(rangeProfit: ProfitDto.KpiGain.Range): ProfitOrderEntity =
        with(rangeProfit) {
            ProfitOrderEntity(
                range = range,
                amount = amount,
                position = pos
            )
        }

    fun map(entity: ProfitEntity): ProfitIndicator = with(entity) {
        return ProfitIndicator(
            campaign = campaign,
            profile = profile,
            region = region,
            zone = zone,
            section = section,
            total = total,
            competitionTotal = competitionTotal,
            competitionCapitalization = competitionCapitalization,
            competition6d6LowValue = competition6d6_low_value,
            competition6d6HighValue = competition6d6_high_value,
            competition6d6Total = competition6d6_total,
            competitionChangeLevel = competitionChangeLevel,
            competitionNewFixed = competitionNewFixed,
            competitionProductsRelease = competitionProductsRelease,
            competitionTacticBonusLevel = competition_tactic_bonus_level,
            competitionTacticBonusAmount = competition_tactic_bonus_amount,
            ordersTotal = ordersTotal,
            ordersPotential = ordersPotential,
            ordersRange = ordersRange.toList().map { map(it) }
        )
    }

    private fun map(range: ProfitOrderEntity): ProfitOrderRange = with(range) {
        ProfitOrderRange(
            range = this.range,
            order = this.position,
            amount = this.amount
        )
    }
}
