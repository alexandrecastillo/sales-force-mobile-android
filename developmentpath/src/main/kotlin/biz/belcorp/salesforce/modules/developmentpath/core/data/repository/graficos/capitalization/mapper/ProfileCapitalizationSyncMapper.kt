package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.mapper

import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.capitalization.cloud.dto.ProfileCapitalizationDto

class ProfileCapitalizationSyncMapper {

    fun map(dto: ProfileCapitalizationDto): List<CapitalizationEntity> =
        dto.kpiCapitalization.map { map(it) }

    private fun map(it: ProfileCapitalizationDto.KpiCapitalization): CapitalizationEntity =
        with(it) {
            return CapitalizationEntity(
                campaign = campaign,
                region = region.orEmpty(),
                zone = zone.orEmpty(),
                section = section.orEmpty(),
                profile = profile,
                capitalizationReal = capitalization.real,
                capitalizationEntries = capitalization.entries,
                capitalizationReentries = capitalization.reentries,
                capitalizationExpenses = capitalization.expenses
            )
        }

}
