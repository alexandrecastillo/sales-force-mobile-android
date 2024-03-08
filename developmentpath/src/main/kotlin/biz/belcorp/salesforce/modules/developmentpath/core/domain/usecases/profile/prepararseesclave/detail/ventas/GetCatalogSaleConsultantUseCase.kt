package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.ventas

import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.NUMBER_SIX
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas.CatalogSaleConsultant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.CatalogSaleConsultantRepository

class GetCatalogSaleConsultantUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val catalogSaleConsultantRepository: CatalogSaleConsultantRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getCatalogSaleConsultant(consultantCode: String): List<CatalogSaleConsultant> {
        val campaigns = getCampaigns()
        return catalogSaleConsultantRepository.getCatalogSaleConsultant(consultantCode, campaigns)
    }

    private fun getCampaigns(): List<String> {
        return campaignsRepository.obtenerCampaniasSincrono(session.llaveUA)
            .takeLast(NUMBER_SIX)
            .map { it.codigo }
    }
}
