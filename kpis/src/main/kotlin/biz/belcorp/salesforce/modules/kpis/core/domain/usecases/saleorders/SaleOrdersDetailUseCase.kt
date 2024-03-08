package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders

import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.formatHyphenIfNull
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersDetail

class SaleOrdersDetailUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository
) {

    fun getInfo(): SaleOrdersDetail {
        val session = requireNotNull(sessionRepository.getSession())
        val campaignCode = getCampaignCode(session)
        return SaleOrdersDetail(session.rol, campaignCode)
    }

    private fun getCampaignCode(session: Sesion): String = with(session) {
        val isBilling = CampaignRules.isBilling(campaign)
        if (isBilling) campaign.nombreCorto else getLastCampaign(llaveUA).nombreCorto
    }

    private fun getLastCampaign(uaKey: LlaveUA): Campania {
        return campaignsRepository.obtenerCampaniaInmediataAnterior(uaKey.formatHyphenIfNull())
    }
}
