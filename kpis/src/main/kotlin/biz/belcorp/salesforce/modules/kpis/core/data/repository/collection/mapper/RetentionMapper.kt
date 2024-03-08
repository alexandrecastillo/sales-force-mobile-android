package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.mapper

import biz.belcorp.salesforce.core.entities.retention.RetentionEntity
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.RetentionDto
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.RetentionIndicator

class RetentionMapper {


    fun map(data: RetentionDto): List<RetentionEntity>{
        val retentions = arrayListOf<RetentionEntity>()

        data.retention.forEach{
            val retention = map(it)
            retentions.add(retention)
        }
        return retentions
    }

    fun map(entity: RetentionEntity): RetentionIndicator = with(entity){
        return RetentionIndicator(
            campaign = campaign,
            profile = profile,
            region = region,
            zone = zone,
            section = section,
            _3d3High = _3d3High,
            _4d4High = _4d4High,
            _5d5High = _5d5High,
            _6d6High = _6d6High,
            retention_percentageHigh = retention_percentageHigh,
            _1d1Low = _1d1Low,
            _2d2Low = _2d2Low,
            _3d3Low = _3d3Low,
            _4d4Low = _4d4Low,
            _5d5Low = _5d5Low,
            _6d6Low = _6d6Low,
            retention_percentageLow = retention_percentageLow
        )
    }

    fun map(retentionIndicator: RetentionIndicator): RetentionEntity = with(retentionIndicator){
        return RetentionEntity(
            campaign = campaign,
            profile = profile,
            region = region,
            zone = zone,
            section = section,
            _3d3High = _3d3High,
            _4d4High = _4d4High,
            _5d5High = _5d5High,
            _6d6High = _6d6High,
            retention_percentageHigh = retention_percentageHigh,
            _1d1Low = _1d1Low,
            _2d2Low = _2d2Low,
            _3d3Low = _3d3Low,
            _4d4Low = _4d4Low,
            _5d5Low = _5d5Low,
            _6d6Low = _6d6Low,
            retention_percentageLow = retention_percentageLow
        )
    }

    fun map(retentionDto: RetentionDto.KpiRetention) : RetentionEntity = with(retentionDto){
        return RetentionEntity(
            campaign = campaign,
            profile = profile,
            region = region!!,
            zone = zone!!,
            section = section!!,
            _3d3High = highValueOrders._3d3,
            _4d4High = highValueOrders._4d4,
            _5d5High = highValueOrders._5d5,
            _6d6High = highValueOrders._6d6,
            retention_percentageHigh = highValueOrders.retentionPercentage,
            _1d1Low = lowValueOrders._1d1,
            _2d2Low = lowValueOrders._2d2,
            _3d3Low = lowValueOrders._3d3,
            _4d4Low = lowValueOrders._4d4,
            _5d5Low = lowValueOrders._5d5,
            _6d6Low = lowValueOrders._6d6,
            retention_percentageLow = lowValueOrders.retentionPercentage
        )
    }

}
