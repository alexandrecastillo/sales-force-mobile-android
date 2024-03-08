package biz.belcorp.salesforce.modules.kpis.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.BonusInfo

interface BonificationRepository {

    suspend fun sync(request: KpiQueryParams)

    suspend fun getBonusInfo(uaKey: LlaveUA, campaign: String): BonusInfo

}
