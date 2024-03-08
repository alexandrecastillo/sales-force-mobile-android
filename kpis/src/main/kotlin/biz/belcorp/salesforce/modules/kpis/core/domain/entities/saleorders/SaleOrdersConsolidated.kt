package biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.deleteHyphen

class SaleOrdersConsolidated(
    val campaign: Campania,
    private val uas: List<GridUaInfo>,
    val configuration: Configuration,
    val data: List<SaleOrdersIndicator>,
    val role: Rol
) {
    var isParent: Boolean = false

    val pairData get() = getPairedData()

    private fun getPairedData(): List<Pair<GridUaInfo, SaleOrdersIndicator?>> {
        return uas.map { ua ->
            val model = data.find {
                filterUa(ua.uaInfo.uaKey, LlaveUA(it.region, it.zone, it.section))
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
