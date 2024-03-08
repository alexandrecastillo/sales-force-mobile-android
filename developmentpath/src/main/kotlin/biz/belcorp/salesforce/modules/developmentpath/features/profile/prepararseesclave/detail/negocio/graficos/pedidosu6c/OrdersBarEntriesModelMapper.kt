package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.pedidosu6c

import biz.belcorp.mobile.components.charts.bar.model.BarCaption
import biz.belcorp.mobile.components.charts.bar.model.BarEntry
import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.mobile.components.charts.bar.model.BarValues
import biz.belcorp.salesforce.core.constants.Constant.ZERO_FLOAT
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.pedidosu6c.OrderU6C
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix

class OrdersBarEntriesModelMapper {

    fun map(modelList: List<OrderU6C>): OrderU6CGraphicModel {
        return OrderU6CGraphicModel(
            values = mapEntrySet(modelList),
            minMaxValues = mapMinMaxValues(modelList)
        )
    }

    private fun mapMinMaxValues(items: List<OrderU6C>) = Pair(
        ZERO_FLOAT,
        items.map { it.orders }.max()?.toFloat() ?: ZERO_FLOAT
    )

    private fun mapEntrySet(modelList: List<OrderU6C>): BarEntrySet {
        val entries = modelList.mapIndexed { index, model -> map(index, model) }
        return BarEntrySet(entries)
    }

    private fun map(index: Int, model: OrderU6C): BarEntry {
        val campaign = model.campaign.maskCampaignWithPrefix()
        val orders = model.orders.toFloat()
        val values = BarValues(index.toFloat(), arrayOf(orders))
        val caption = BarCaption(campaign, orders.toInt().toString())
        return BarEntry(caption, values)
    }

}
