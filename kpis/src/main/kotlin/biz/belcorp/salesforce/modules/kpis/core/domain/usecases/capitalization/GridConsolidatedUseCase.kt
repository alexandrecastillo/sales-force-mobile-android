package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization

import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.utils.removeHyphens
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.GridUaInfo
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CapitalizationRepository
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.model.CapitalizationConsolidated

class GridConsolidatedUseCase(
    private val sessionUseCase: ObtenerSesionUseCase,
    private val uaInfoUseCase: UaInfoUseCase,
    private val campaignRepository: CampaniasRepository,
    private val capitalizationRepository: CapitalizationRepository
) {

    private val session get() = sessionUseCase.obtener()

    suspend fun getCapitalizationInfo(uaKey: LlaveUA?): CapitalizationConsolidated {
        val ua = (uaKey ?: session.llaveUA).removeHyphens()
        val uas = getUas(ua)
        val gridUas = getGridUaInfo(uas)
        val campaign = getCampaign()
        val capitalization = getCapitalization(ua, campaign.codigo)
        val isBilling =  CampaignRules.isBilling(session.campaign)
        return CapitalizationConsolidated(
            gridUas,
            capitalization,
            session.rol,
            campaign,
            session.campaign,
            isBilling
        )
    }

    private suspend fun getUas(uaKey: LlaveUA): List<UaInfo> {
        return uaInfoUseCase.getAssociatedUaListByUaKey(uaKey)
    }

    private suspend fun getCapitalization(
        uaKey: LlaveUA,
        campaignCode: String
    ): List<CapitalizationIndicator> {
        return capitalizationRepository.getConsolidatedByUa(uaKey, campaignCode)
    }

    private fun getLastCampaign(uaKey: LlaveUA): Campania {
        return campaignRepository.obtenerCampaniaInmediataAnterior(uaKey)
    }

    private fun getCampaign(): Campania {
        val isBilling = CampaignRules.isBilling(session.campaign)
        return if (isBilling) session.campaign else getLastCampaign(session.llaveUA)
    }

    private fun getGridUaInfo(uaInfo: List<UaInfo>): List<GridUaInfo> {
        return uaInfo.map { GridUaInfo(it, false) }
    }

}
