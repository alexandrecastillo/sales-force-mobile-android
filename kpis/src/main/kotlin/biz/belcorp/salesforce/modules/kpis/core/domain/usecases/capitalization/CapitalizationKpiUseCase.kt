package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization

import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CapitalizationRepository

class CapitalizationKpiUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val capitalizationRepository: CapitalizationRepository
) {

    private val session by lazy {
        requireNotNull(sessionRepository.getSession())
    }
    private val prevCampaign by lazy {
        requireNotNull(campaignsRepository.obtenerCampaniaInmediataAnterior(session.llaveUA))
    }
    private val currentCampaign by lazy {
        requireNotNull(campaignsRepository.obtenerCampaniaActual(session.llaveUA))
    }

    val isBilling by lazy { CampaignRules.isBilling(session.campaign) }

    private val campaignSingleName by lazy { getCampaignCodeByPeriod(isBilling) }


    suspend fun getKpiData(ua: LlaveUA?): CapitalizationIndicator {
        val uaKey = ua ?: session.llaveUA
        val campaigns = listOf(campaignSingleName)
        val list = capitalizationRepository.getKpiDataByCampaignsAndUa(uaKey, campaigns)
        return list.firstOrNull() ?: CapitalizationIndicator()
    }

    private fun getCampaignCodeByPeriod(isBilling: Boolean) =
        if (isBilling) currentCampaign.codigo else prevCampaign.codigo

    fun isParent(ua: LlaveUA) = session.llaveUA.isEqual(ua)

}
