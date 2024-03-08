package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.ventas

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.BrightPathLevelEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.BrightPathNextLevelEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.BrightPathNextLevelOrderEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.BrightPathNextLevelSalesEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.BrightPeriodEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.ConsultantSaleEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.CurrentPackEntity
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.orZero
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.perfil.sales.SaleConsultantDto

class SaleConsultantMapper {

    fun map(
        currentEntities: List<ConsultantSaleEntity>,
        entities: List<SaleConsultantDto.SaleConsultant>
    ): List<ConsultantSaleEntity> {
        val newEntities = entities.map(::map)
        return getConsultantSaleEntities(currentEntities, newEntities)
    }

    private fun map(entity: SaleConsultantDto.SaleConsultant): ConsultantSaleEntity = with(entity) {
        val consultant = ConsultantSaleEntity(
            campaign = campaign.orEmpty(),
            region = region.orEmpty(),
            zone = zone.orEmpty(),
            section = section.orEmpty(),
            consultantCode = consultantCode.orEmpty(),
            netSale = netSale ?: 0.0,
            catalogSale = catalogSale ?: 0.0,
            billedOrderAmount = billedOrderAmount ?: 0.0,
            isHighOrderValue = isHighOrderValue ?: false,
            averageSaleLastSixCampaigns = averageSaleLastSixCampaigns ?: 0.0,
            constantU6c = constantU6c ?: false,
            gainAmount = gainAmount ?: 0.0,
            brightPathPeriod_ = brightPathPeriod_.orEmpty()

        )

        if (currentPack.isNotNull()) {
            currentPack!!.forEach { cp ->
                consultant.currentPack.add(map(cp))
            }
        }

        if (brightPathPeriod.isNotNull()) {
            consultant.brightPeriod.add(map(brightPathPeriod!!))
        }

        if (brightPathLevel.isNotNull()) {
            consultant.brightPathLevel.add(map(brightPathLevel!!))
        }

        if (brightPathNextLevel.isNotNull()) {
            consultant.brightPathNextLevel.add(map(brightPathNextLevel!!))
        }

        return consultant
    }

    private fun map(entity: SaleConsultantDto.SaleConsultant.CurrentPack): CurrentPackEntity = with(entity) {
        return CurrentPackEntity(
            campaign = campaign.orEmpty(),
            brandCode = brandCode.orEmpty(),
            brand = brand.orEmpty(),
            average = average ?: 0.0,
            amount = amount ?: 0
        )
    }

    private fun map(entity: SaleConsultantDto.SaleConsultant.BrightPeriod): BrightPeriodEntity = with(entity) {
        return BrightPeriodEntity(
            orders = orders ?: 0,
            sale = sale ?: 0.0
        )
    }

    private fun map(entity: SaleConsultantDto.SaleConsultant.BrightPathLevel): BrightPathLevelEntity = with(entity) {
        return BrightPathLevelEntity(
            name = name.orEmpty(),
            accumulativeSales = accumulativeSales ?: 0.0,
            accumulativeOrders = accumulativeOrders.orEmpty(),
            hasOrder = hasOrder ?: false,
            salesReal = salesReal ?: 0.0,
            salesAverage = salesAverage ?: 0.0,
            salesToRetainLevel = salesToRetainLevel ?: 0.0,
            currentLevelSalesRequirement = currentLevelSalesRequirement ?: 0.0,
            currentLevelOrderRequirement = currentLevelOrderRequirement ?: 0,
            campaignProgress = campaignProgress ?: 0,
            averageCurrentLevel = averageCurrentLevel ?: 0.0,
        )
    }

    private fun map(entity: SaleConsultantDto.SaleConsultant.BrightPathNextLevel): BrightPathNextLevelEntity = with(entity) {
        val level = BrightPathNextLevelEntity(name = name.orZero())
        if (entity.orders.isNotNull()) {
            level.brightPathNextLevelOrder.add(map(entity.orders!!))
        }
        if (entity.sales.isNotNull()) {
            level.brightPathNextLevelSales.add(map(entity.sales!!))
        }

        return level
    }

    private fun map(entity: SaleConsultantDto.SaleConsultant.BrightPathNextLevel.Orders): BrightPathNextLevelOrderEntity = with(entity) {
        return BrightPathNextLevelOrderEntity(requirement = requirement ?: 0)
    }

    private fun map(entity: SaleConsultantDto.SaleConsultant.BrightPathNextLevel.Sales): BrightPathNextLevelSalesEntity = with(entity) {
        return BrightPathNextLevelSalesEntity(
            name = name.orEmpty(),
            salesRequirement = salesRequirement ?: 0.0,
            average = average ?: 0.0,
        )
    }


    private fun getConsultantSaleEntities(
        currentEntities: List<ConsultantSaleEntity>,
        newEntities: List<ConsultantSaleEntity>
    ): List<ConsultantSaleEntity> {
        val entities = arrayListOf<ConsultantSaleEntity>()
        newEntities.forEach { newEntity ->
            val exists =
                currentEntities.any { currentEntity -> predicate(newEntity, currentEntity) }
            if (!exists) entities.add(newEntity)
        }
        return entities
    }

    private fun predicate(
        newEntity: ConsultantSaleEntity,
        currentEntity: ConsultantSaleEntity
    ): Boolean {
        return getUaKey(newEntity).isEqual(getUaKey(currentEntity)) &&
            newEntity.consultantCode == currentEntity.consultantCode &&
            newEntity.campaign == currentEntity.campaign
    }

    private fun getUaKey(entity: ConsultantSaleEntity) =
        LlaveUA(entity.region, entity.zone, entity.section)

}
