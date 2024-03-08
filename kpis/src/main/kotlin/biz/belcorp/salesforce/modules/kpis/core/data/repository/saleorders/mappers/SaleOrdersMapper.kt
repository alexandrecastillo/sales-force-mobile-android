package biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.mappers

import biz.belcorp.salesforce.core.constants.CountryISO
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersOrderEntity
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersSaleEntity
import biz.belcorp.salesforce.core.utils.concatPercentageSymbol
import biz.belcorp.salesforce.core.utils.orZero
import biz.belcorp.salesforce.core.utils.toDoubleOrZero
import biz.belcorp.salesforce.core.utils.toPercentageNumber
import biz.belcorp.salesforce.core.utils.toStringOrNull
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.cloud.dto.SaleOrdersDto
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersOrderRange
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersSaleRange

const val LOW_VALUE_PLUS = "Pedidos Bajo Valor Plus"

class SaleOrdersMapper {

    fun map(data: SaleOrdersDto): List<SaleOrdersEntity> {


        /*if (country != CountryISO.PERU && country != CountryISO.ECUADOR) {
      val index = data.saleOrders[0].order.ranges.indexOfFirst { it.range == LOW_VALUE_PLUS }
      if (index > -1) {
          val tempList = data.saleOrders[0].order.ranges.toMutableList()
          tempList.removeAt(index)
          data.saleOrders[0].order.ranges = tempList
      }
  }*/
        val saleOrders = arrayListOf<SaleOrdersEntity>()

        data.saleOrders.forEach {kpiSaleOrder ->
            //Remove "Bajo valor plus" --LOW_VALUE_PLUS--,  of each item in saleOrdersCollection

            val index = kpiSaleOrder.order.ranges.indexOfFirst { it.range == LOW_VALUE_PLUS }
            if (index > -1) {
                val tempList = kpiSaleOrder.order.ranges.toMutableList()
                tempList.removeAt(index)
                kpiSaleOrder.order.ranges = tempList
            }

            val saleOrder = map(kpiSaleOrder)
            val orderRanges = kpiSaleOrder.order.ranges.map { rangeOrder -> map(rangeOrder) }
            val saleRanges = kpiSaleOrder.sale.ranges.map { rangeSale -> map(rangeSale) }
            saleOrder.ordersRange.addAll(orderRanges)
            saleOrder.salesRange.addAll(saleRanges)
            saleOrders.add(saleOrder)
        }
        return saleOrders
    }

    fun map(data: SaleOrdersDto.KpiSaleOrders): SaleOrdersEntity = with(data) {
        return SaleOrdersEntity(
            campaign = campaign,
            region = region.orEmpty(),
            zone = zone.orEmpty(),
            section = section.orEmpty(),
            profile = profile,
            netSale = sale.netSaleReal,
            netSaleGoal = sale.netSaleGoal,
            fulfillmentNetSalePercentage = sale.fulfillmentNetSalesPercentage,
            catalogSale = sale.catalogSale,
            catalogSaleGoal = sale.catalogSaleGoal,
            fulfillmentCatalogSalesPercentage = sale.fulfillmentCatalogSalesPercentage,
            orders = order.real,
            ordersGoal = order.goal,
            highValueOrders = order.highValueOrders,
            lowValueOrders = order.lowValueOrders,
            averageAmount = order.averageAmount,
            averageAmountGoal = order.averageAmountGoal,
            fulfillmentOrderPercentage = order.fulfillmentOrderPercentage,
            fulfillmentOrderAveragePercentage = order.fulfillmentOrderAveragePercentage,
            activityPercentage = activity.percentage,
            activityGoal = activity.goal,
            activityPegs = activity.pegs,
            fulfillmentActivityPercentage = activity.fulfillmentPercentage,
            activesInitials = actives.initials,
            activesFinals = actives.finals,
            activesFinalsLastYear = actives.finalsLastYear,
            activesRetentionPercentage = actives.retentionPercentage.zeroIfNull(),
            fulfillmentRetentionPercentage = actives.fulfillmentRetentionPercentage.zeroIfNull(),
            activesRetentionGoal = actives.retentionGoal,
            multibrandPercentage = multibrand?.percentage.zeroIfNull().toString(),
            multibrandActives = multibrand?.actives.zeroIfNull().toString(),
            multibrandLastCampaign = multibrand?.lastCampaign.zeroIfNull().toString(),
            multibrandNoMultibrandActives = multibrand?.noMultibrandActives.zeroIfNull().toString(),
            multibrandVsLastCampaign = multibrand?.vsLastCampaign.zeroIfNull().toString(),
            multibrandNumVsLastCampaign = multibrand?.numVsLastCampaign.zeroIfNull().toString(),
            multibrandActivesHighValue = multibrand?.activesHighValue.zeroIfNull().toString(),
            multibrandOrdersHighValue = multibrand?.ordersHighValue.zeroIfNull().toString(),
            multibrandHighValueNumVsLastCampaign = multibrand?.highValueNumVsLastcampaign.zeroIfNull().toString()
        )
    }

    private fun map(
        rangeOrder: SaleOrdersDto.KpiSaleOrders.Order.RangeOrder
    ): SaleOrdersOrderEntity =
        with(rangeOrder) {
            SaleOrdersOrderEntity(
                range = range,
                amount = quantity,
                position = pos
            )
        }

    private fun map(
        rangeSale: SaleOrdersDto.KpiSaleOrders.Sale.RangeSale
    ): SaleOrdersSaleEntity = with(rangeSale) {
        SaleOrdersSaleEntity(
            range = range,
            amount = amount,
            position = pos
        )
    }

    fun map(entity: SaleOrdersEntity): SaleOrdersIndicator = with(entity) {
        SaleOrdersIndicator(
            campaign = campaign,
            region = region,
            zone = zone,
            section = section,
            profile = profile,
            netSale = netSale,
            netSaleGoal = netSaleGoal,
            fulfillmentNetSalePercentage = fulfillmentNetSalePercentage,
            catalogSale = catalogSale,
            catalogSaleGoal = catalogSaleGoal,
            fulfillmentCatalogSalesPercentage = fulfillmentCatalogSalesPercentage,
            orders = orders,
            ordersGoal = ordersGoal,
            highValueOrders = highValueOrders,
            lowValueOrders = lowValueOrders,
            averageAmount = averageAmount,
            averageAmountGoal = averageAmountGoal,
            fulfillmentOrderPercentage = fulfillmentOrderPercentage,
            fulfillmentOrderAveragePercentage = fulfillmentOrderAveragePercentage,
            activityPercentage = activityPercentage,
            activityGoal = activityGoal,
            activityPegs = activityPegs,
            fulfillmentActivityPercentage = fulfillmentActivityPercentage,
            activesInitials = activesInitials,
            activesFinals = activesFinals,
            activesFinalsLastYear = activesFinalsLastYear,
            activesRetentionPercentage = activesRetentionPercentage,
            fulfillmentRetentionPercentage = fulfillmentRetentionPercentage,
            activesRetentionGoal = activesRetentionGoal,
            multibrandActives = multibrandActives,
            multibrandPercentage = formatToPercentageWithSymbol(multibrandPercentage.toDoubleOrZero()),
            multibrandLastCampaign = multibrandLastCampaign,
            multibrandNoMultibrandActives = multibrandNoMultibrandActives,
            multibrandVsLastCampaign = multibrandVsLastCampaign,
            multibrandNumVsLastCampaign = multibrandNumVsLastCampaign,
            multibrandHighValueNumVsLastCampaign = multibrandHighValueNumVsLastCampaign,
            multibrandOrdersHighValue = multibrandOrdersHighValue,
            ordersRange = ordersRange.toList().map { map(it) },
            salesRange = salesRange.toList().map { map(it) }
        )
    }

    private fun map(range: SaleOrdersOrderEntity): SaleOrdersOrderRange = with(range) {
        SaleOrdersOrderRange(
            range = this.range,
            position = this.position,
            amount = this.amount
        )
    }

    private fun map(range: SaleOrdersSaleEntity): SaleOrdersSaleRange = with(range) {
        SaleOrdersSaleRange(
            range = this.range,
            position = this.position,
            amount = this.amount
        )
    }

    fun map(entity: SaleOrdersIndicator): SaleOrdersEntity = with(entity) {
        SaleOrdersEntity(
            campaign = campaign,
            region = region,
            zone = zone,
            section = section,
            profile = profile,
            netSale = netSale,
            netSaleGoal = netSaleGoal,
            fulfillmentNetSalePercentage = fulfillmentNetSalePercentage,
            catalogSale = catalogSale,
            catalogSaleGoal = catalogSaleGoal,
            fulfillmentCatalogSalesPercentage = fulfillmentCatalogSalesPercentage,
            orders = orders,
            ordersGoal = ordersGoal,
            highValueOrders = highValueOrders,
            lowValueOrders = lowValueOrders,
            averageAmount = averageAmount,
            averageAmountGoal = averageAmountGoal,
            fulfillmentOrderPercentage = fulfillmentOrderPercentage,
            fulfillmentOrderAveragePercentage = fulfillmentOrderAveragePercentage,
            activityPercentage = activityPercentage,
            activityGoal = activityGoal,
            activityPegs = activityPegs,
            fulfillmentActivityPercentage = fulfillmentActivityPercentage,
            activesInitials = activesInitials,
            activesFinals = activesFinals,
            activesFinalsLastYear = activesFinalsLastYear,
            activesRetentionPercentage = activesRetentionPercentage,
            fulfillmentRetentionPercentage = fulfillmentRetentionPercentage,
            activesRetentionGoal = activesRetentionGoal
        )
    }

    private fun formatToPercentageWithSymbol(value: Double): String {
        return value.toPercentageNumber().toStringOrNull().orZero().concatPercentageSymbol()
    }

}
