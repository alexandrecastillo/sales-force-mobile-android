package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.ventas

import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.ConsultantSaleEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.BrightPathSaleRepository

class SaleBrightPathConsultantUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val brightPathConsultantRepository: BrightPathSaleRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getSaleBrightPathConsultant(consultantCode: String): List<ConsultantSaleEntity> {
        val campaigns = getCampaigns()
        return brightPathConsultantRepository.getSaleBrightPathConsultant(consultantCode, campaigns)
    }

    private fun getCampaigns(): List<String> {
        return campaignsRepository.obtenerCampaniasSincrono(session.llaveUA)
            .takeLast(Constant.NUMBER_SIX)
            .map { it.codigo }
    }
}
