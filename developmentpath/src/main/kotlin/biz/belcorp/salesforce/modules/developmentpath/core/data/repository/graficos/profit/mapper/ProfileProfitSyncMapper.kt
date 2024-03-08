package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.mapper

import biz.belcorp.salesforce.core.entities.collection.ProfitEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.profit.cloud.dto.ProfileProfitDto

class ProfileProfitSyncMapper {

    fun map(dto: ProfileProfitDto): List<ProfitEntity> =
        dto.kpiGain.map { map(it) }

    private fun map(it: ProfileProfitDto.KpiGain): ProfitEntity =
        with(it) {
            return ProfitEntity(
                campaign = campaign,
                region = region.orEmpty(),
                zone = zone.orEmpty(),
                section = section.orEmpty(),
                profile = profile,
                total = total
            )
        }

}
