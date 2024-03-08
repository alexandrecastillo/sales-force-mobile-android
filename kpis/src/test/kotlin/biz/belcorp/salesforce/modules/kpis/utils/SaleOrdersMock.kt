package biz.belcorp.salesforce.modules.kpis.utils

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.configuration.MainBrandType
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import java.util.*

object SaleOrdersMock {

    val uaKeyMock = LlaveUA("09", "0906", "A", 0L)

    val campaignMock =
        Campania("202004", "C-18", Date(), Date(), Date(), 0, true, Campania.Periodo.VENTA)

    val lastCampaignMock =
        Campania("201917", "C-17", Date(), Date(), Date(), 0, true, Campania.Periodo.VENTA)

    val configurationMock =
        Configuration("CO", "Colombia", "$", "+71", 0, 0, true, MainBrandType.LBEL, false, false, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, Constant.NUMERO_UNO, false)

    val kpiRequestParamsMock = KpiQueryParams(
        configurationMock.country,
        listOf(campaignMock.codigo, lastCampaignMock.codigo),
        "GZ",
        "20",
        "2057",
        "",
        showZones = true
    )
}
