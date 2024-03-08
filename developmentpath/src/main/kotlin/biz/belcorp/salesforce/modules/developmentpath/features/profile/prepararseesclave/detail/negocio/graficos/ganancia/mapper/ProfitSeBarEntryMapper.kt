package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.mapper

import biz.belcorp.mobile.components.charts.bar.model.BarCaption
import biz.belcorp.mobile.components.charts.bar.model.BarEntry
import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.mobile.components.charts.bar.model.BarValues
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.model.ProfitSeModel
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix

class ProfitSeBarEntryMapper {

    fun map(modelList: List<ProfitSeModel>): BarEntrySet {
        val entries = modelList.mapIndexed { index, model -> map(index, model) }
        return BarEntrySet(entries)
    }

    fun map(index: Int, model: ProfitSeModel): BarEntry {
        val campaign = model.campaign.maskCampaignWithPrefix()
        val total = model.total.zeroIfNull()
        val values = BarValues(index.toFloat(), arrayOf(total))
        return BarEntry(BarCaption(campaign), values)
    }
}
