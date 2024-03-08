package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.mappers

import biz.belcorp.mobile.components.charts.bar.model.BarCaption
import biz.belcorp.mobile.components.charts.bar.model.BarEntry
import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet
import biz.belcorp.mobile.components.charts.bar.model.BarValues
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.models.ActivesRetentionModel
import biz.belcorp.salesforce.modules.developmentpath.utils.maskCampaignWithPrefix

class RetencionBarEntryMapper {

    fun map(modelList: List<ActivesRetentionModel>): BarEntrySet {
        val entries = modelList.mapIndexed { index, model -> map(index, model) }
        return BarEntrySet(entries)
    }

    fun map(index: Int, model: ActivesRetentionModel): BarEntry {
        val campania = model.campaign.maskCampaignWithPrefix()
        val activasReal = model.activesReal.toFloat()
        val values = BarValues(index.toFloat(), arrayOf(activasReal))
        val caption = BarCaption(campania, activasReal.toInt().toString())
        return BarEntry(caption, values)
    }
}
