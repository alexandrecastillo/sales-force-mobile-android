package biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection

import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isEqual

class CollectionConsolidated(
    val collectionList: List<CollectionIndicator>,
    val uaList: List<UaInfo>,
    val currencySymbol: String,
    val campaign: String,
    val role: Rol,
    val isThirdPerson: Boolean
) {

    val pairData get() = getPairedData()

    private fun getPairedData(): List<Pair<UaInfo, CollectionIndicator?>> {
        return uaList.map { ua ->
            val model = collectionList.find {
                ua.uaKey.isEqual(LlaveUA(it.region, it.zone, it.section))
            }
            Pair(ua, model)
        }
    }

}
