package biz.belcorp.salesforce.core.domain.usecases.searchfilters

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.searchfilters.SearchFiltersRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.removeHyphens

class SearchFiltersUseCase(
    private val searchFiltersRepository: SearchFiltersRepository,
    private val sesionRepository: SessionRepository
) {

    private val session get() = requireNotNull(sesionRepository.getSession())

    suspend fun sync(): SyncType {
        val ua = session.llaveUA.removeHyphens()
        return if (isRolEnable(ua)) {
            searchFiltersRepository.sync(session.zonificacion)
        } else {
            SyncType.None
        }
    }

    private fun isRolEnable(ua: LlaveUA) =
        ua.roleAssociated in arrayOf(Rol.GERENTE_ZONA, Rol.SOCIA_EMPRESARIA)

}
