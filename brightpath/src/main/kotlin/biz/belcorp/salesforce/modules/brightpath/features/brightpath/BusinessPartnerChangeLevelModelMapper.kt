package biz.belcorp.salesforce.modules.brightpath.features.brightpath

import biz.belcorp.salesforce.core.entities.businesspartner.*

class BusinessPartnerChangeLevelModelMapper {

    fun mapToModel(
        businessPartnerChangeLevelEntityList: MutableList<BusinessPartnerChangeLevelEntity>?,
    ): List<BusinessPartnerChangeLevelModel> {
        return businessPartnerChangeLevelEntityList!!.map {
            mapBusinessPartnerChangeLevelToModel(it)
        }
    }

    private fun mapBusinessPartnerChangeLevelToModel(
        businessPartnerChangeLevelEntity:
        BusinessPartnerChangeLevelEntity
    ) =
        BusinessPartnerChangeLevelModel(
            campaign = businessPartnerChangeLevelEntity.campaign,
            profile = businessPartnerChangeLevelEntity.profile,
            region = businessPartnerChangeLevelEntity.region,
            zone = businessPartnerChangeLevelEntity.zone,
            section = businessPartnerChangeLevelEntity.section,
            consultantCode = businessPartnerChangeLevelEntity.consultantCode,
            campaignRequirement = businessPartnerChangeLevelEntity.campaignRequirement,
            sectionSales = businessPartnerChangeLevelEntity.sectionSales,
            sectionOrders = businessPartnerChangeLevelEntity.sectionOrders,
            gainAmountLowValue = businessPartnerChangeLevelEntity.gainAmountLowValue,
            gainAmountLowValuePlus = businessPartnerChangeLevelEntity.gainAmountLowValuePlus,
            gainAmountHighValue = businessPartnerChangeLevelEntity.gainAmountHighValue,
            gainAmountHighValuePlus = businessPartnerChangeLevelEntity.gainAmountHighValuePlus,
            level = mapBusinessPartnerChangeLevelLevelEntityToModel(businessPartnerChangeLevelEntity.level.last()),
            nextLevel = businessPartnerChangeLevelEntity.nextLevel.map(::map).last(),
            requirement = businessPartnerChangeLevelEntity.levelRequirement.map(::map)
        )

    private fun mapBusinessPartnerChangeLevelLevelEntityToModel(
        businessPartnerChangeLevelEntity:
        BusinessPartnerChangeLevelLevelEntity?
    ):
        BusinessPartnerLevelModel = BusinessPartnerLevelModel(
            code = businessPartnerChangeLevelEntity?.code,
            name = businessPartnerChangeLevelEntity?.name,
            consecutiveCampaigns = businessPartnerChangeLevelEntity?.consecutiveCampaigns,
            campaignsNotAccomplished = businessPartnerChangeLevelEntity?.campaignsNotAccomplished,
            accomplished = businessPartnerChangeLevelEntity?.accomplished
        )

    private fun map(data: BusinessPartnerChangeSalesEntity): BusinessPartnerSalesModel =
        with(data) {
            return BusinessPartnerSalesModel(
                requirement = requirement,
                real = real,
                accomplished = accomplished,
            )
        }

    private fun map(data: BusinessPartnerChangeOrdersEntity): BusinessPartnerOrdersModel =
        with(data) {
            return BusinessPartnerOrdersModel(
                requirement = requirement,
                real = real,
                accomplished = accomplished,
            )
        }

    private fun map(data: BusinessPartnerChangeLevelNextLevelEntity): BusinessPartnerChangeNextLevelModel =
        with(data) {


            val sales = sales.map(::map).last()
            val orders = orders.map(::map).last()

            return BusinessPartnerChangeNextLevelModel(
                name = name,
                accomplished = accomplished,
                campaigns_accomplished = campaigns_accomplished,
                sales = sales,
                orders = orders
            )
        }


    private fun map(data: BusinessPartnerChangeLevelRequirementEntity): BusinessPartnerRequirementModel =
        with(data) {
            return BusinessPartnerRequirementModel(
                level = level,
                minimumSales = minimumSales,
                minimumOrders = minimumOrders
            )
        }
}
