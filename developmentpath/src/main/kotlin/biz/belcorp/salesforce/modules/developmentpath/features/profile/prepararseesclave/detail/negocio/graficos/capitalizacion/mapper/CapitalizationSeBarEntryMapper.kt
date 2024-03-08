package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.mapper

import biz.belcorp.mobile.components.charts.bar.model.BarCaption
import biz.belcorp.mobile.components.charts.bar.model.BarEntry
import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.mobile.components.charts.bar.model.BarValues
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.model.CapitalizationSeModel
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix

class CapitalizationSeBarEntryMapper {

    fun map(modelList: List<CapitalizationSeModel>): BarEntrySet {
        val entries = modelList.mapIndexed { index, model -> map(index, model) }
        return BarEntrySet(entries)
    }

    fun map(index: Int, model: CapitalizationSeModel): BarEntry {
        val campaign = model.campaign.maskCampaignWithPrefix()
        val entries = model.entries.toFloat()
        val reentries = model.reentries.toFloat()
        val expenses = model.expenses.toFloat()
        val values = BarValues(index.toFloat(), arrayOf(entries, reentries, expenses))
        return BarEntry(BarCaption(campaign), values)
    }
}
