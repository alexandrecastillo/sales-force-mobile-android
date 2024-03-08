package biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.mapper

import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.cloud.dto.CapitalizationDto
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator

class CapitalizationMapper {

    fun map(data: CapitalizationDto): List<CapitalizationEntity> {
        return data.kpiCapitalization.map { map(it) }
    }

    private fun map(entity: CapitalizationDto.KpiCapitalization): CapitalizationEntity =
        with(entity) {
            CapitalizationEntity(
                campaign = campaign,
                region = region.orEmpty(),
                zone = zone.orEmpty(),
                section = section.orEmpty(),
                profile = profile,
                capitalizationReal = capitalization.real,
                capitalizationGoal = capitalization.goal,
                capitalizationFulfillment = capitalization.capitalizationFulfillment,
                capitalizationProjected = capitalization.projected,
                capitalizationEntries = capitalization.entries,
                capitalizationEntriesGoal = capitalization.entriesGoal,
                capitalizationReentries = capitalization.reentries,
                capitalizationExpenses = capitalization.expenses,
                pegsReal = pegs.real,
                pegRetentionGoal = pegs.retention.goal,
                pegRetentionReal = pegs.retention.real,
                pegRetentionPercentage = pegs.retention.percentage,
                pegRetentionRemaining = pegs.retention.remaining,
                potentialEntries = potential.entries,
                potentialReentries = potential.reentries,
                potentialTotal = potential.total,
                capitalizationProactive = capitalization.proactive,
                capitalizationSuccess = capitalization.success
            )
        }


    fun map(entity: CapitalizationEntity): CapitalizationIndicator =
        with(entity) {
            CapitalizationIndicator(
                campaign = campaign,
                region = region,
                zone = zone,
                section = section,
                capitalizationReal = capitalizationReal,
                capitalizationGoal = capitalizationGoal,
                capitalizationFulfillment = capitalizationFulfillment,
                capitalizationProjected = capitalizationProjected,
                capitalizationEntries = capitalizationEntries,
                capitalizationEntriesGoal = capitalizationEntriesGoal,
                capitalizationReentries = capitalizationReentries,
                capitalizationExpenses = capitalizationExpenses,
                pegsReal = pegsReal,
                pegRetentionGoal = pegRetentionGoal,
                pegRetentionReal = pegRetentionReal,
                pegRetentionPercentage = pegRetentionPercentage,
                pegRetentionRemaining = pegRetentionRemaining,
                potentialTotal = potentialTotal,
                potentialEntries = potentialEntries,
                potentialReentries = potentialReentries,
                capitalizationProactive = capitalizationProactive,
                capitalizationSuccess = capitalizationSuccess
            )
        }
}
