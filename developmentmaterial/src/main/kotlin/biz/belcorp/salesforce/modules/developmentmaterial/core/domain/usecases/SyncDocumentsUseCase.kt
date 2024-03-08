package biz.belcorp.salesforce.modules.developmentmaterial.core.domain.usecases

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.repository.MaterialesDesarrolloRepository


class SyncDocumentsUseCase(
    private val sessionRepository: SessionRepository,
    private val materialesDesarrolloRepository: MaterialesDesarrolloRepository
) {

    suspend fun sync() {
        val rol = requireNotNull(sessionRepository.getSession()?.codigoRol)
        materialesDesarrolloRepository.syncDocuments(rol)
    }

}
