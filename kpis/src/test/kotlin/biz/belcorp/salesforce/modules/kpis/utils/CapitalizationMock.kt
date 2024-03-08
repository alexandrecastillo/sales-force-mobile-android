package biz.belcorp.salesforce.modules.kpis.utils

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import java.util.*

object CapitalizationMock {

    val uaKeyMock = LlaveUA("09", "0906", "A", 0L)

    val campaignMock =
        Campania("202004", "C-04", Date(), Date(), Date(), 0, true, Campania.Periodo.VENTA)

}
