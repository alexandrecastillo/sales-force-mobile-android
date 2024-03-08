package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model

import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.deleteHyphen

class NewCycleConsolidated(
    val uaList: List<UaInfo>,
    val values: List<NewCycleGridIndicatorModel>,
    val role: Rol,
    val isBilling: Boolean,
    val isParent: Boolean
) {

    val pairData get() = getPairedData()

    private fun getPairedData(): List<Pair<UaInfo, NewCycleGridIndicatorModel?>> {
        return uaList.map { ua ->
            val model = values.find {
                filterUa(ua.uaKey, LlaveUA(it.region, it.zone, it.section))
            }
            Pair(ua, model)
        }
    }

    private fun filterUa(ua: LlaveUA, otherUa: LlaveUA): Boolean {
        return ua.codigoRegion.orEmpty().deleteHyphen() == otherUa.codigoRegion.orEmpty().deleteHyphen() &&
            ua.codigoZona.orEmpty().deleteHyphen() == otherUa.codigoZona.orEmpty().deleteHyphen() &&
            ua.codigoSeccion.orEmpty().deleteHyphen() == otherUa.codigoSeccion.orEmpty().deleteHyphen()
    }
}

