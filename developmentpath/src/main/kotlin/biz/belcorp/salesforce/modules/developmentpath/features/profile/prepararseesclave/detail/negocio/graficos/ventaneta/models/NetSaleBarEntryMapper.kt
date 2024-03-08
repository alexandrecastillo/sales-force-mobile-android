package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.models

import biz.belcorp.mobile.components.charts.bar.model.BarCaption
import biz.belcorp.mobile.components.charts.bar.model.BarEntry
import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.mobile.components.charts.bar.model.BarValues
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix

class NetSaleBarEntryMapper {
    fun map(modelList: List<NetSaleSeModel>): BarEntrySet {
        val entries = modelList.mapIndexed { index, model -> map(index, model) }
        return BarEntrySet(entries)
    }

    fun map(index: Int, model: NetSaleSeModel): BarEntry {
        val campaign = model.campaign.maskCampaignWithPrefix()
        val amount = model.amount
        val values = BarValues(index.toFloat(), arrayOf(amount))
        return BarEntry(BarCaption(campaign), values)
    }
}
