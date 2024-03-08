package biz.belcorp.salesforce.modules.kpis.core.domain.usecases

import biz.belcorp.salesforce.core.constants.PeriodType
import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiDetailParams

class KpiDetailParamsUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository
) {
    private val session by lazy { requireNotNull(sessionRepository.getSession()) }
    private val campaign by lazy { getCurrentCampaign() }

    fun getParams(kpiType: Int): KpiDetailParams = with(session) {
        KpiDetailParams(
            llaveUA,
            rol,
            getPeriodType(),
            kpiType
        )
    }

    private fun getCurrentCampaign() =
        requireNotNull(campaignsRepository.obtenerCampaniaActual(session.llaveUA))

    private fun getPeriodType(): Int {
        return if (CampaignRules.isBilling(campaign)) PeriodType.BILLING else PeriodType.SALE
    }
}
