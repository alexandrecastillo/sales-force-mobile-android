package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ranking

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ranking.RankingGraphic
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ranking.ProfileRankingRepository

class GetRankingUseCase(
    private val sessionRepository: SessionRepository,
    private val campaignsRepository: CampaniasRepository,
    private val profileRankingRepository: ProfileRankingRepository,
    private val personRepository: RddPersonaRepository
) {

    private val session get() = requireNotNull(sessionRepository.getSession())
    private val campaigns by lazy { getPreviousCampaigns() }

    suspend fun getRankingInfo(personIdentifier: PersonIdentifier): List<RankingGraphic> {
        val person = personIdentifier.run { personRepository.recuperarPersonaPorId(id, role) }
        val uaKey = requireNotNull(person?.llaveUA)
        val previousCampaigns = campaigns.takeLast(Constant.NUMBER_SIX)
        return profileRankingRepository.getRankingInfo(uaKey, previousCampaigns)
    }

    private fun getPreviousCampaigns(): List<String> {
        val campaigns = campaignsRepository.obtenerPenultimasCampanias(session.llaveUA)
        return campaigns.map { it.codigo }.sorted()
    }

}
