package biz.belcorp.salesforce.modules.digital.core.domain.entities

import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isEqual

class DigitalConsolidated(
    val digitalInfolist: List<DigitalInfo>,
    val uaList: List<UaInfo>,
    val campaign: String,
    val role: Rol,
    val isThirdPerson: Boolean
) {

    val pairData get() = getPairedData()

    private fun getPairedData(): List<Pair<UaInfo, DigitalInfo?>> {
        return uaList.map { ua ->
            val model = digitalInfolist.find {
                ua.uaKey.isEqual(LlaveUA(it.region, it.zone, it.section))
            }
            Pair(ua, model)
        }
    }

}
