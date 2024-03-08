package biz.belcorp.salesforce.modules.brightpath.core.data.mapper

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelLevelEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelNextLevelEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeLevelRequirementEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeOrdersEntity
import biz.belcorp.salesforce.core.entities.businesspartner.BusinessPartnerChangeSalesEntity
import biz.belcorp.salesforce.modules.brightpath.core.data.network.dto.BusinessPartnerChangeLevelDto

class BusinessPartnerChangeLevelMapper {

    fun map(
        original: BusinessPartnerChangeLevelDto,
    ): List<BusinessPartnerChangeLevelEntity> {
        return original.businessPartnerChangeLevel.map(::map)
    }

    private fun map(data: BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel): BusinessPartnerChangeLevelEntity = with(data) {
        val entity = BusinessPartnerChangeLevelEntity(
            campaign = campaign,
            profile = profile,
            region = region,
            zone = zone,
            section = section,
            consultantCode = consultantCode,
            campaignRequirement = campaignRequirement,
            sectionSales = sectionSales,
            sectionOrders = sectionOrders ?: NUMBER_ZERO,
            gainAmountLowValue = gainAmountLowValue,
            gainAmountLowValuePlus = gainAmountLowValuePlus,
            gainAmountHighValue = gainAmountHighValue,
            gainAmountHighValuePlus = gainAmountHighValuePlus
        )

        entity.level.add(map(level))
        entity.nextLevel.add(map(nextLevel))
        entity.levelRequirement.addAll(levelRequirement.map(::map))

        return entity
    }

    private fun map(data: BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.Level): BusinessPartnerChangeLevelLevelEntity = with(data) {
        return BusinessPartnerChangeLevelLevelEntity(
            code = code,
            name = name,
            consecutiveCampaigns = consecutiveCampaigns,
            campaignsNotAccomplished = campaignsNotAccomplished,
            accomplished = accomplished,
        )
    }

    private fun map(data: BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.NextLevel): BusinessPartnerChangeLevelNextLevelEntity =
        with(data) {
            val nextLevel = BusinessPartnerChangeLevelNextLevelEntity(
                name = name,
                accomplished = accomplished,
                campaigns_accomplished = campaigns_accomplished,
            )

            nextLevel.sales.add(map(sales))
            nextLevel.orders.add(map(orders))


            return nextLevel
        }

    private fun map(data: BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.NextLevel.Sales): BusinessPartnerChangeSalesEntity =
        with(data) {
            return BusinessPartnerChangeSalesEntity(
                requirement = requirement,
                real = real,
                accomplished = accomplished,
            )
        }

    private fun map(data: BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.NextLevel.Orders): BusinessPartnerChangeOrdersEntity =
        with(data) {
            return BusinessPartnerChangeOrdersEntity(
                requirement = requirement,
                real = real,
                accomplished = accomplished,
            )
        }

    private fun map(data: BusinessPartnerChangeLevelDto.BusinessPartnerChangeLevel.LevelRequirement): BusinessPartnerChangeLevelRequirementEntity =
        with(data) {
            return BusinessPartnerChangeLevelRequirementEntity(
                level = level,
                minimumSales = minimumSales,
                minimumOrders = minimumOrders
            )
        }


}
