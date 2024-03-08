package biz.belcorp.salesforce.modules.virtualmethodology.core.domain.usecases

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.repository.GroupSegRepository


class SyncGroupSegUseCase(
    private val sessionRepository: SessionRepository,
    private val groupSegRepository: GroupSegRepository
) {

    suspend fun sync() {
        val sesion = requireNotNull(sessionRepository.getSession())
        groupSegRepository.syncGroups(sesion.countryIso, sesion.zonificacion)
    }

}
