package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.campaignprojection

import biz.belcorp.salesforce.core.entities.campaignprojection.CampaignProjectionEntity
import biz.belcorp.salesforce.core.entities.campaignprojection.CampaignProjectionOrderEntity
import biz.belcorp.salesforce.core.entities.campaignprojection.CampaignProjectionRetentionEntity
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CampaignProjectionModel
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CampaignProjectionRetentionModel
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.OrderModel
import io.objectbox.relation.ToMany

class CampaignProjectionModelMapper {

    fun mapToModel(
        currencySymbol: String,
        campaignProjectionEntity: CampaignProjectionEntity?,
    ): CampaignProjectionModel =
        CampaignProjectionModel(
            region = campaignProjectionEntity?.region,
            section = campaignProjectionEntity?.section,
            zone = campaignProjectionEntity?.zone,
            dateCreated = campaignProjectionEntity?.dateCreated,
            dateEdited = campaignProjectionEntity?.dateEdited,
            campaign = campaignProjectionEntity?.campaign,
            projectedCapitalization = campaignProjectionEntity?.projectedCapitalization,
            pegsRetentionExpected = campaignProjectionEntity?.pegsRetentionExpected,
            pegsRetentionReal = campaignProjectionEntity?.pegsRetentionReal,
            finalProjectedActives = campaignProjectionEntity?.finalProjectedActives,
            entriesProjected = campaignProjectionEntity?.entriesProjected,
            entriesReal = campaignProjectionEntity?.entriesReal,
            reEntriesProjected = campaignProjectionEntity?.reEntriesProjected,
            reEntriesReal = campaignProjectionEntity?.reEntriesReal,
            pegsInMySection = campaignProjectionEntity?.pegsInMySection,
            initialActives = campaignProjectionEntity?.initialActives,
            finalsLastYearActives = campaignProjectionEntity?.finalsLastYearActives,
            currentTotalOrders = campaignProjectionEntity?.currentTotalOrders,
            ordersExpectedTotal = campaignProjectionEntity?.ordersExpectedTotal,
            ordersTotalGain = campaignProjectionEntity?.ordersTotalGain,
            orderList = mapOrdersListEntityToModel(campaignProjectionEntity?.ordersList!!),
            currencySymbol = currencySymbol,
            reEntriesLatest5C = campaignProjectionEntity.reEntriesLast5C,
            retentionList = mapRetentionToModel(campaignProjectionEntity.retentionList!!),
            retentionGain6d6Low = campaignProjectionEntity.retentionGain6d6Low,
            retentionGain6d6High = campaignProjectionEntity.retentionGain6d6High,
            retentionGain6d6HighMultiBrand = campaignProjectionEntity.retentionGain6d6HighMultiBrand,
            capiGainByPointProjectionCurrent = campaignProjectionEntity.capiGainByPointProjectionCurrent,
            capiGainByPointProjectionReal = campaignProjectionEntity.capiGainByPointProjectionReal,
            capitalizationCurrent = campaignProjectionEntity.projectedCapitalization,
            capitalizationReal = campaignProjectionEntity.capitalizationReal,
            gainCurrent = campaignProjectionEntity.gainCurrent,
            gainReal = campaignProjectionEntity.gainReal,
        )

    private fun mapOrdersListEntityToModel(orderList: List<CampaignProjectionOrderEntity>):
        List<OrderModel> {
        return orderList.map {
            mapOrderEntityToModel(it)
        }
    }


    private fun mapOrderEntityToModel(orderEntity: CampaignProjectionOrderEntity):
        OrderModel {
        return OrderModel(
            title = orderEntity.title,
            unitsExpected = orderEntity.unitsExpected,
            unitsReal = orderEntity.unitsReal,
            gainPerOrder = orderEntity.gainPerOrder,
            gainPerOrderNotSuccess = orderEntity.gainPerOrderNotSuccess,
            gainPerOrderExpected = orderEntity.gainPerOrderExpected,
        )
    }

    private fun mapRetentionToModel(retentionList: ToMany<CampaignProjectionRetentionEntity>): List<CampaignProjectionRetentionModel> {
        return retentionList.map {
            mapRetentionItem(it)
        }
    }

    private fun mapRetentionItem(campaignProjectionRetentionEntity: CampaignProjectionRetentionEntity): CampaignProjectionRetentionModel {
        return CampaignProjectionRetentionModel(
            title = campaignProjectionRetentionEntity.title,
            activeConsultant = campaignProjectionRetentionEntity.activeConsultant,
            retentionExpected = campaignProjectionRetentionEntity.retentionExpected,
            retentionReal = campaignProjectionRetentionEntity.retentionReal
        )
    }


    fun mapToEntity(
        campaignProjectionModel: CampaignProjectionModel?,
    ): CampaignProjectionEntity =
        CampaignProjectionEntity(
            region = campaignProjectionModel?.region,
            section = campaignProjectionModel?.section,
            zone = campaignProjectionModel?.zone,
            dateCreated = campaignProjectionModel?.dateCreated,
            dateEdited = campaignProjectionModel?.dateEdited,
            campaign = campaignProjectionModel?.campaign,
            projectedCapitalization = campaignProjectionModel?.projectedCapitalization,
            pegsRetentionExpected = campaignProjectionModel?.pegsRetentionExpected,
            finalProjectedActives = campaignProjectionModel?.finalProjectedActives,
            entriesProjected = campaignProjectionModel?.entriesProjected,
            entriesReal = campaignProjectionModel?.entriesReal,
            reEntriesProjected = campaignProjectionModel?.reEntriesProjected,
            reEntriesReal = campaignProjectionModel?.reEntriesReal,
            pegsInMySection = campaignProjectionModel?.pegsInMySection,
            initialActives = campaignProjectionModel?.initialActives,
            finalsLastYearActives = campaignProjectionModel?.finalsLastYearActives,
            currentTotalOrders = campaignProjectionModel?.currentTotalOrders,
            ordersExpectedTotal = campaignProjectionModel?.ordersExpectedTotal,
            ordersTotalGain = campaignProjectionModel?.ordersTotalGain,
            reEntriesLast5C = campaignProjectionModel?.reEntriesLatest5C,
            retentionGain6d6High = campaignProjectionModel?.retentionGain6d6High,
            retentionGain6d6Low = campaignProjectionModel?.retentionGain6d6Low,
            capiGainByPointProjectionCurrent = campaignProjectionModel?.capiGainByPointProjectionCurrent,
            capiGainByPointProjectionReal = campaignProjectionModel?.capiGainByPointProjectionReal,
            capitalizationCurrent = campaignProjectionModel?.projectedCapitalization,
            capitalizationReal = campaignProjectionModel?.capitalizationReal,
            gainCurrent = campaignProjectionModel?.gainCurrent,
            gainReal = campaignProjectionModel?.gainReal,
        ).apply {
            ordersList.addAll(mapOrdersListModelToEntity(campaignProjectionModel?.orderList!!))
            retentionList.addAll(mapRetentionListEntityToModel(campaignProjectionModel.retentionList))
        }

    private fun mapOrdersListModelToEntity(orderList: List<OrderModel>):
        List<CampaignProjectionOrderEntity> {
        if (orderList.isEmpty()) {
            return emptyList()
        } else {
            return orderList.map {
                mapOrderModelToEntity(it)
            }
        }
    }

    private fun mapOrderModelToEntity(orderModel: OrderModel):
        CampaignProjectionOrderEntity {
        return CampaignProjectionOrderEntity(
            title = orderModel.title,
            unitsExpected = orderModel.unitsExpected,
            unitsReal = orderModel.unitsReal,
            gainPerOrder = orderModel.gainPerOrder,
            gainPerOrderNotSuccess = orderModel.gainPerOrderNotSuccess,
            gainPerOrderExpected = orderModel.gainPerOrderExpected,
        )
    }

    private fun mapRetentionListEntityToModel(retentionList: List<CampaignProjectionRetentionModel>):
        List<CampaignProjectionRetentionEntity> {
        return retentionList.map {
            mapRetentionEntityToModel(it)
        }
    }

    private fun mapRetentionEntityToModel(retentionModel: CampaignProjectionRetentionModel):
        CampaignProjectionRetentionEntity {
        return CampaignProjectionRetentionEntity(
            title = retentionModel.title,
            retentionExpected = retentionModel.retentionExpected,
            retentionReal = retentionModel.retentionReal,
            activeConsultant = retentionModel.activeConsultant,
        )
    }
}
