package biz.belcorp.salesforce.modules.brightpath.core.domain.usecases

import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.brightpath.core.data.network.dto.BusinessPartnerChangeLevelParams
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.BusinessPartnerChangeLevelRepository

private const val NUMBER_SIX = 6
private const val IM_SELLING = "F"

class BusinessPartnerChangeLevelUseCase(
    private val repository: BusinessPartnerChangeLevelRepository,
    private val sessionManager: SessionRepository,
    private val campaignsRepository: CampaniasRepository
) {

    private val session get() = requireNotNull(sessionManager.getSession())

    suspend fun syncConsultantSale() {

        val campaigns = getPreviousCampaigns(session.llaveUA).takeLast(NUMBER_SIX).toMutableList()

        if (UserProperties.session?.campaign?.getPhase() == IM_SELLING) {
            campaigns.add(getCurrentCampaigns(session.llaveUA)!!.codigo)
        }

        val params = BusinessPartnerChangeLevelParams(
            country = session.countryIso,
            consultantCode = session.codigoConsultora,
            campaigns = campaigns,
            region = session.region.orEmpty(),
            zone = session.zona.orEmpty(),
            section = session.seccion.orEmpty()
        )

        return repository.sync(params)

    }

    suspend fun syncConsultantSalePartner(uaKey: LlaveUA, consultantCode: String) {

        val campaigns = getPreviousCampaigns(uaKey).takeLast(NUMBER_SIX)

        val params = BusinessPartnerChangeLevelParams(
            country = session.countryIso,
            consultantCode = consultantCode,
            campaigns = campaigns,
            region = uaKey.codigoRegion.orEmpty(),
            zone = uaKey.codigoZona.orEmpty(),
            section = uaKey.codigoSeccion.orEmpty()
        )

        return repository.sync(params)

    }

    private fun getPreviousCampaigns(uaKey: LlaveUA): List<String> {
        val previousCampaigns = campaignsRepository.obtenerPenultimasCampanias(uaKey)
        return previousCampaigns.map { it.codigo }.sorted()
    }

    private fun getCurrentCampaigns(uaKey: LlaveUA): Campania? {
        val previousCampaigns = campaignsRepository.obtenerCampaniaActual(uaKey)
        return previousCampaigns
    }
}
