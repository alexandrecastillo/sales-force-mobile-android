package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.session

import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository

@Deprecated("Use ObtenerSesionUseCase, just to get Session with Persona object")
class ObtenerSesionPersonaUseCase(
    private val sessionPersonRepository: SessionPersonRepository
) {

    fun obtener(): Sesion {
        return sessionPersonRepository.get()
    }

}
