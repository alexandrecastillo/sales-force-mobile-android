package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.mapper

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.entities.campaignprojection.CampaignProjectionEntity
import biz.belcorp.salesforce.core.entities.campaignprojection.CampaignProjectionOrderEntity
import biz.belcorp.salesforce.core.entities.campaignprojection.CampaignProjectionRetentionEntity
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.dto.CampaignProjectionDto
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.cloud.dto.SaveCampaignProjectionDto

class CampaignProjectionMapper {

    fun mapToEntity(campaignProjection: CampaignProjectionDto):
        CampaignProjectionEntity =
        CampaignProjectionEntity(
            region = campaignProjection.campaignProjection.region,
            section = campaignProjection.campaignProjection.section,
            zone = campaignProjection.campaignProjection.zone,
            entriesProjected = campaignProjection.campaignProjection.capitalization.entries.projected,
            entriesReal = campaignProjection.campaignProjection.capitalization.entries.real,
            reEntriesProjected = campaignProjection.campaignProjection.capitalization.reEntries.projected,
            reEntriesReal = campaignProjection.campaignProjection.capitalization.reEntries.real,
            dateCreated = campaignProjection.campaignProjection.dateCreated,
            dateEdited = campaignProjection.campaignProjection.dateEdited,
            campaign = campaignProjection.campaignProjection.campaign,
            pegsInMySection = campaignProjection.campaignProjection.capitalization.pegs.current,
            pegsRetentionExpected = campaignProjection.campaignProjection.capitalization.pegs.retentionExpected,
            pegsRetentionReal = campaignProjection.campaignProjection.capitalization.pegs.retentionReal,
            initialActives = campaignProjection.campaignProjection.capitalization.activesConsultant.currentActives,
            finalProjectedActives = campaignProjection.campaignProjection.capitalization.activesConsultant.activesExpected,
            projectedCapitalization = campaignProjection.campaignProjection.capitalization.activesConsultant.expected,
            finalsLastYearActives = campaignProjection.campaignProjection.capitalization.activesConsultant.finalsLastYearActives,
            reEntriesLast5C = campaignProjection.campaignProjection.retention6D6.reEntriesLast5C,
            currentTotalOrders = campaignProjection.campaignProjection.orders.currentTotal,
            ordersExpectedTotal = campaignProjection.campaignProjection.orders.expectedTotal,
            ordersTotalGain = campaignProjection.campaignProjection.orders.totalGain,
            retentionGain6d6Low = campaignProjection.campaignProjection.retention6D6.retentionGain6d6Low,
            retentionGain6d6High = campaignProjection.campaignProjection.retention6D6.retentionGain6d6High,
            retentionGain6d6HighMultiBrand = campaignProjection.campaignProjection.retention6D6.retentionGain6d6HighMultibrand ?: Constant.ZERO_FLOAT,
            capiGainByPointProjectionCurrent = campaignProjection.campaignProjection.capitalization.gainByPoints.current,
            capiGainByPointProjectionReal = campaignProjection.campaignProjection.capitalization.gainByPoints.real,
            capitalizationCurrent = campaignProjection.campaignProjection.capitalization.capi.current,
            capitalizationReal =campaignProjection.campaignProjection.capitalization.capi.real,
            gainCurrent = campaignProjection.campaignProjection.capitalization.gain.current,
            gainReal = campaignProjection.campaignProjection.capitalization.gain.real,

        ).apply {
            this.ordersList.addAll(mapOrderListToEntity(campaignProjection.campaignProjection.orders.orderList))
            this.retentionList.addAll(mapRetentionList(campaignProjection.campaignProjection.retention6D6))
        }

    private fun mapOrderListToEntity(
        list: List<CampaignProjectionDto.CampaignProjection.Orders.Order>
    ): List<CampaignProjectionOrderEntity> {
        return list.map {
            mapOrderWithCampaignProjectionOrderEntity(it)
        }
    }

    private fun mapOrderWithCampaignProjectionOrderEntity(
        order:
        CampaignProjectionDto.
        CampaignProjection.Orders.Order
    ):
        CampaignProjectionOrderEntity {
        return CampaignProjectionOrderEntity(
            title = order.title,
            unitsExpected = order.unitsExpected,
            unitsReal = order.unitsReal,
            gainPerOrder = order.gainPerOrder,
            gainPerOrderNotSuccess = order.gainPerOrderNotSuccess,
            gainPerOrderExpected = order.gainPerOrderExpected,
        )
    }

    private fun mapRetentionList(retention6D6: CampaignProjectionDto.CampaignProjection.Retention6d6): Collection<CampaignProjectionRetentionEntity> {
        return retention6D6.retentionList.map {
            mapRetentionToCampaignProjectRetentionEntity(it)
        }
    }

    private fun mapRetentionToCampaignProjectRetentionEntity(retention: CampaignProjectionDto.CampaignProjection.Retention6d6.Retention): CampaignProjectionRetentionEntity {
        return CampaignProjectionRetentionEntity(
            title = retention.title,
            activeConsultant = retention.activeConsultants,
            retentionExpected = retention.retentionExpected,
            retentionReal = retention.retentionReal,
        )
    }

    fun mapToDto(campaignProjection: CampaignProjectionEntity):
        SaveCampaignProjectionDto =
        SaveCampaignProjectionDto(
            region = campaignProjection.region,
            section = campaignProjection.section,
            zone = campaignProjection.zone,
            campaign = campaignProjection.campaign,
            capitalization = mapCapitalizationToModel(campaignProjection),
            retention6D6 = mapRetentionToModel(campaignProjection),
            orders = mapOrdersToModel(campaignProjection),
            activityProjectedPercentage = NUMBER_ZERO,
            pegsProjectedNextCampaign = NUMBER_ZERO,
        )
}

private fun mapCapitalizationToModel(campaignProjection: CampaignProjectionEntity):
    SaveCampaignProjectionDto.Capitalization {
    return SaveCampaignProjectionDto.Capitalization(
        SaveCampaignProjectionDto.Capitalization.Entries(
            campaignProjection.entriesProjected,
            campaignProjection.entriesReal
        ),
        SaveCampaignProjectionDto.Capitalization.ReEntries(
            campaignProjection.reEntriesProjected,
            campaignProjection.reEntriesReal
        ),
        SaveCampaignProjectionDto.Capitalization.Pegs(
            current = campaignProjection.pegsInMySection,
            retentionExpected = campaignProjection.pegsRetentionExpected,
            leaveExpected = NUMBER_ZERO,
            campaignProjection.pegsRetentionReal
        ),
        SaveCampaignProjectionDto.Capitalization.ActiveConsultants(
            activesExpected = campaignProjection.finalProjectedActives,
            currentActives = campaignProjection.initialActives,
            expected = campaignProjection.projectedCapitalization,
            finalsLastYearActives = campaignProjection.finalsLastYearActives,
        ),
        SaveCampaignProjectionDto.Capitalization.CapitalizationProjected(
            current = campaignProjection.capitalizationCurrent,
            real = campaignProjection.capitalizationReal,
        ),
        SaveCampaignProjectionDto.Capitalization.GainByPoint(
            current = campaignProjection.capiGainByPointProjectionCurrent,
            real = campaignProjection.capiGainByPointProjectionReal,
        ),
        SaveCampaignProjectionDto.Capitalization.Gain(
            current = campaignProjection.gainCurrent,
            real = campaignProjection.gainReal,
        ),
    )
}

private fun mapOrdersToModel(campaignProjection: CampaignProjectionEntity):
    SaveCampaignProjectionDto.Orders {
    return SaveCampaignProjectionDto.Orders(
        currentTotal = campaignProjection.currentTotalOrders,
        expectedTotal = campaignProjection.ordersExpectedTotal,
        totalGain = campaignProjection.ordersTotalGain,
        orderList = mapOrderListToDto(campaignProjection.ordersList)
    )

}

private fun mapOrderListToDto(
    list: List<CampaignProjectionOrderEntity>
): List<SaveCampaignProjectionDto.Orders.Order> {
    return list.map {
        mapOrderWithCampaignProjectionOrder(it)
    }
}

private fun mapOrderWithCampaignProjectionOrder(
    order:
    CampaignProjectionOrderEntity
):
    SaveCampaignProjectionDto.Orders.Order {
    return SaveCampaignProjectionDto.Orders.Order(
        title = order.title,
        unitsExpected = order.unitsExpected,
        unitsReal = order.unitsReal,
        gainPerOrder = order.gainPerOrder,
        gainPerOrderNotSuccess = order.gainPerOrderNotSuccess,
        gainPerOrderExpected = order.gainPerOrderExpected,
    )
}

private fun mapRetentionToModel(campaignProjection: CampaignProjectionEntity):
    SaveCampaignProjectionDto.Retention6d6 {
    return SaveCampaignProjectionDto.Retention6d6(
        reEntriesLast5C = campaignProjection.reEntriesLast5C,
        retentionGain6d6Low = campaignProjection.retentionGain6d6Low,
        retentionGain6d6High = campaignProjection.retentionGain6d6High,
        retentionList = mapRetentionListToDto(campaignProjection.retentionList)
    )

}

private fun mapRetentionListToDto(
    list: List<CampaignProjectionRetentionEntity>
): List<SaveCampaignProjectionDto.Retention6d6.Retention> {
    return list.map {
        mapOrderWithCampaignProjectionRetention(it)
    }
}

private fun mapOrderWithCampaignProjectionRetention(
    retention:
    CampaignProjectionRetentionEntity
):
    SaveCampaignProjectionDto.Retention6d6.Retention {
    return SaveCampaignProjectionDto.Retention6d6.Retention(
        title = retention.title,
        activeConsultants = retention.activeConsultant,
        retentionExpected = retention.retentionExpected,
        retentionReal = retention.retentionReal,
    )
}
