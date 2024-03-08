package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile

import android.util.Log
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.concursos.ConcursosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.DatosPerfilRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.ProfileSyncUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.SyncUseCase

class SyncProfileUseCase(
    private val sessionRepository: SessionPersonRepository,
    private val personaRepository: RddPersonaRepository,
    private val syncUseCase: SyncUseCase,
    private val profileSyncUseCase: ProfileSyncUseCase,
    private val datosPerfilRepository: DatosPerfilRepository,
    private val concursosRepository: ConcursosRepository
) {

    private val session get() = requireNotNull(sessionRepository.get())

    companion object {
        private const val DIFERENCIA_MAXIMA_CARGA_PEFIL_A_DEMANDA_DV = 2
        private const val DIFERENCIA_MAXIMA_CARGA_PEFIL_A_DEMANDA_OTROS_ROLES = 3
    }

    suspend fun sync(personIdentifier: PersonIdentifier) {
        val person = getPersonById(personIdentifier)
        legacySyncIfNeeded(person)
        syncIfNeeded(person)
    }

    private suspend fun legacySyncIfNeeded(person: PersonaRdd) {
        if (isOnDemandAllowed(person.rol)) {
            syncUseCase.uploadProfileInfo()
            datosPerfilRepository.sync(person)
        }
    }

    private suspend fun syncIfNeeded(person: PersonaRdd) {
        if (person.rol.isPC()) return
        concursosRepository.sync(person, session.countryIso)
        profileSyncUseCase.sync(person.rol, person.llaveUA, person.personCode)
    }

    private fun getPersonById(personIdentifier: PersonIdentifier) = with(personIdentifier) {
        requireNotNull(personaRepository.recuperarPersonaPorId(id, role))
    }

    private fun isOnDemandAllowed(rolPersona: Rol): Boolean = with(session) {
        return when {
            rol.isDV() ->
                rol diferenciaDeNivel rolPersona >= DIFERENCIA_MAXIMA_CARGA_PEFIL_A_DEMANDA_DV
            rol.isGR() || rol.isGZ() || rol.isSE() ->
                rol diferenciaDeNivel rolPersona >= DIFERENCIA_MAXIMA_CARGA_PEFIL_A_DEMANDA_OTROS_ROLES
            else -> throw UnsupportedRoleException(rol)
        }
    }

}
