package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cobranza.CobranzaCampaniaAnterior
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza.DatosCobranzaCampaniaAnteriorRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository

class ObtenerDatosCobranzaCampaniaAnteriorUseCase(
    private val cobranzaRepository: DatosCobranzaCampaniaAnteriorRepository,
    private val personaRepository: RddPersonaRepository,
    private val campaniasRepository: CampaniasRepository,
    private val sessionRepository: SessionRepository
) {

    private val session get() = requireNotNull(sessionRepository.getSession())

    suspend fun obtener(personIdentifier: PersonIdentifier): Response {
        val person = getPersonById(personIdentifier)
        val campaign = campaniasRepository.getPreviousCampaignSuspend(session.llaveUA)
        val collection = cobranzaRepository.obtener(person.llaveUA, campaign.codigo)
        return Response(collection, campaign.nombreCorto)
    }

    private fun getPersonById(personIdentifier: PersonIdentifier) = with(personIdentifier) {
        requireNotNull(personaRepository.recuperarPersonaPorId(id, role))
    }

    class Response(
        val cobranza: CobranzaCampaniaAnterior,
        val campaniaAnterior: String
    )
}
