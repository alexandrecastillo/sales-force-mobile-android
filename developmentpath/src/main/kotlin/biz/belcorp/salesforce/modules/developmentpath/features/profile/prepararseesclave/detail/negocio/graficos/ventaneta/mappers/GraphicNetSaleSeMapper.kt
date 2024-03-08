package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.mappers

import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.NetSaleSe
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.models.GraphicNetSaleModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.models.NetSaleSeModel

class GraphicNetSaleSeMapper : Mapper<NetSaleSeModel, NetSaleSe>() {

    override fun map(value: NetSaleSeModel) = NetSaleSe()

    override fun reverseMap(value: NetSaleSe): NetSaleSeModel {
        return with(value) {
            NetSaleSeModel(
                campaign = campaign,
                average = average.toFloat(),
                amount = amount.toFloat()
            )
        }
    }

    fun mapToModel(values: List<NetSaleSeModel>, entrySet: BarEntrySet): GraphicNetSaleModel {
        val maxValue = values.map { it.amount }.maxBy { it }.zeroIfNull()
        return GraphicNetSaleModel(
            netSales = values,
            barEntrySet = entrySet,
            minValue = ZERO_DECIMAL.toFloat(),
            maxValue = maxValue
        )
    }
}
