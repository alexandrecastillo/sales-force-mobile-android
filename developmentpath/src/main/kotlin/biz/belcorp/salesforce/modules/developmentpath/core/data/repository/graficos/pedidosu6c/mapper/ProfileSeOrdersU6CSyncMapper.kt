package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.mapper

import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.cloud.dto.ProfileSeOrdersU6CDto

class ProfileSeOrdersU6CSyncMapper {

    fun map(dto: ProfileSeOrdersU6CDto): List<SaleOrdersEntity> =
        dto.saleOrders.map { mapToSaleOrders(it) }

    private fun mapToSaleOrders(it: ProfileSeOrdersU6CDto.ProfileSeOrdersU6C): SaleOrdersEntity =
        with(it) {
            return SaleOrdersEntity(
                campaign = campaign,
                region = region.orEmpty(),
                zone = zone.orEmpty(),
                section = section.orEmpty(),
                profile = profile,
                netSale = sale.netSaleReal,
                netSaleGoal = sale.netSaleGoal,
                fulfillmentNetSalePercentage = sale.fulfillmentNetSalesPercentage,
                orders = order.real,
                activesFinals = actives.finals,
                activesFinalsLastYear = actives.finalsLastYear
            )
        }

}
