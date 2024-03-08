package biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.mapper

import biz.belcorp.salesforce.core.entities.bonification.BonificationEntity
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.cloud.dto.BonusDto

class BonificationMapper {

    fun map(dto: BonusDto): List<BonificationEntity> {
        return dto.list.map { map(it) }
    }

    private fun map(item: BonusDto.Bonus): BonificationEntity {
        return BonificationEntity(
            campaign = item.campaign,
            region = item.region,
            zone = item.zone,
            section = item.section,
            code = item.code,
            amount = item.amount
        )
    }

}
