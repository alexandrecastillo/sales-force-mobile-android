package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.ventas

import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas.GainConsultantContainer
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.GainConsultantRepository

class GetGainConsultantUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val gainConsultantRepository: GainConsultantRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getGainConsultant(consultantCode: String): GainConsultantContainer {
        val campaigns = getCampaigns()
        val gains = gainConsultantRepository.getGainConsultant(consultantCode, campaigns)
        return GainConsultantContainer(gains).apply {
            bestGain = gains.maxBy { it.amount }
            lastGain = gains.maxBy { it.campaign }
        }
    }

    private fun getCampaigns(): List<String> {
        return campaignsRepository.obtenerCampaniasSincrono(session.llaveUA)
            .takeLast(Constant.NUMBER_SIX)
            .map { it.codigo }
    }
}
