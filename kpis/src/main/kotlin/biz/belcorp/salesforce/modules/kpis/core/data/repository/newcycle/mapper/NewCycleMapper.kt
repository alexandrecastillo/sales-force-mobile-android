package biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.mapper

import biz.belcorp.salesforce.core.entities.newcycles.NewCycleEntity
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.cloud.dto.NewCycleQuery
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator

class NewCycleMapper {

    fun map(newCycleIndicator: NewCycleQuery.Data): List<NewCycleEntity> {
        return newCycleIndicator.kpiRetention.map { map(it) }
    }

    private fun map(kpiRetention: NewCycleQuery.Data.KpiRetention): NewCycleEntity =
        with(kpiRetention) {
            return NewCycleEntity(
                campaign = campaign,
                region = region.orEmpty(),
                zone = zone.orEmpty(),
                section = section.orEmpty(),
                profile = profile,
                highValueOrders6d6 = highValueOrders.i6d6.zeroIfNull(),
                highValueOrders5d5 = highValueOrders.i5d5.zeroIfNull(),
                highValueOrders4d4 = highValueOrders.i4d4.zeroIfNull(),
                highValueOrders3d3 = highValueOrders.i3d3.zeroIfNull(),
                highValueOrders2d2 = highValueOrders.i2d2.zeroIfNull(),
                highValueOrders1d1 = highValueOrders.i1d1.zeroIfNull(),
                highValueOrdersRetentionPercentage = highValueOrders.retentionPercentage,
                lowValueOrders6d6 = lowValueOrders.i6d6.zeroIfNull(),
                lowValueOrders5d5 = lowValueOrders.i5d5.zeroIfNull(),
                lowValueOrders4d4 = lowValueOrders.i4d4.zeroIfNull(),
                lowValueOrders3d3 = lowValueOrders.i3d3.zeroIfNull(),
                lowValueOrders2d2 = lowValueOrders.i2d2.zeroIfNull(),
                lowValueOrders1d1 = lowValueOrders.i1d1.zeroIfNull(),
                lowValueOrdersRetentionPercentage = lowValueOrders.retentionPercentage
            )
        }

    fun map(entity: NewCycleEntity): NewCycleIndicator = with(entity) {
        return NewCycleIndicator(
            campaign = campaign,
            region = region,
            zone = zone,
            section = section,
            profile = profile,
            highValueOrders6d6 = highValueOrders6d6,
            highValueOrders5d5 = highValueOrders5d5,
            highValueOrders4d4 = highValueOrders4d4,
            highValueOrders3d3 = highValueOrders3d3,
            highValueOrders2d2 = highValueOrders2d2,
            highValueOrders1d1 = highValueOrders1d1,
            highValueOrdersRetentionPercentage = highValueOrdersRetentionPercentage,
            lowValueOrders6d6 = lowValueOrders6d6,
            lowValueOrders5d5 = lowValueOrders5d5,
            lowValueOrders4d4 = lowValueOrders4d4,
            lowValueOrders3d3 = lowValueOrders3d3,
            lowValueOrders2d2 = lowValueOrders2d2,
            lowValueOrders1d1 = lowValueOrders1d1,
            lowValueOrdersRetentionPercentage = lowValueOrdersRetentionPercentage
        )
    }
}

