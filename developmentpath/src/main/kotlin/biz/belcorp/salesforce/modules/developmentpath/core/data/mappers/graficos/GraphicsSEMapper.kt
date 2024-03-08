package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.graficos

import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity
import biz.belcorp.salesforce.core.entities.collection.ProfitEntity
import biz.belcorp.salesforce.core.entities.saleorders.SaleOrdersEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.ActivesRetention
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.CapitalizationSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.NetSaleSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.ProfitSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.pedidosu6c.OrderU6C

class GraphicsSEMapper {

    fun parse(model: CapitalizationEntity): CapitalizationSe {
        return with(model) {
            CapitalizationSe(
                campaign = campaign,
                real = capitalizationReal,
                entries = capitalizationEntries,
                reentries = capitalizationReentries,
                expenses = capitalizationExpenses
            )
        }
    }

    fun parseToRetention(model: SaleOrdersEntity): ActivesRetention = with(model) {
        return ActivesRetention(
            campaign = campaign,
            activesReal = activesFinals,
            activesLastYear = activesFinalsLastYear
        )
    }

    fun parseToSales(model: SaleOrdersEntity): NetSaleSe = with(model) {
        return NetSaleSe(
            campaign = campaign,
            amount = netSale,
            average = ZERO_DECIMAL
        )
    }

    fun parseToOrders(model: SaleOrdersEntity): OrderU6C = with(model) {
        return OrderU6C(
            campaign = campaign,
            orders = orders
        )
    }

    fun parse(model: ProfitEntity): ProfitSe {
        return with(model) {
            ProfitSe(
                campaign = campaign,
                total = total.toFloat()
            )
        }
    }

}
