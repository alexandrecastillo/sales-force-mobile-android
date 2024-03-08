package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.model

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.GridUaInfo

class CapitalizationConsolidated(
    val uas: List<GridUaInfo>,
    val values: List<CapitalizationIndicator>,
    val role: Rol,
    val campaign: Campania,
    val currentCampaign: Campania,
    val isBilling: Boolean
) {

    val pairData get() = getPairedData()

    private fun getPairedData(): List<Pair<LlaveUA, CapitalizationIndicator?>> {
        return uas.map { ua ->
            val model = values.filter {
                ua.uaInfo.uaKey.isEqual(LlaveUA(it.region, it.zone, it.section))
            }
            Pair(ua.uaInfo.uaKey, model.firstOrNull())
        }
    }

}
