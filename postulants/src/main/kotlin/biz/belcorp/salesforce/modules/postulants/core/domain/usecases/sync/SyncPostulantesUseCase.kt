package biz.belcorp.salesforce.modules.postulants.core.domain.usecases.sync

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol.GERENTE_REGION
import biz.belcorp.salesforce.core.entities.zonificacion.Rol.GERENTE_ZONA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol.SOCIA_EMPRESARIA
import biz.belcorp.salesforce.core.utils.isNull
import biz.belcorp.salesforce.core.utils.removeHyphens
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.PostulantesRepository
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.sync.SyncPostulantesRepository
import io.reactivex.Completable

internal class SyncPostulantesUseCase(
    private val syncRepository: SyncPostulantesRepository,
    private val sesionRepository: SessionRepository,
    private val postulantesRepository: PostulantesRepository
) {

    private val session get() = requireNotNull(sesionRepository.getSession())

    suspend fun sync(): SyncType {
        val ua = session.llaveUA.removeHyphens()
        return if (isRolEnable(ua)) {
            syncRepository.sync(session.zonificacion)
        } else {
            SyncType.None
        }
    }

    fun syncApplications(): SyncType {
        val upload = subirPendientes().blockingGet()
        if (upload.isNull()) return SyncType.PartiallyUpdated()
        else throw upload
    }

    private fun subirPendientes(): Completable {
        return Completable.merge(listarUploads())
    }

    private fun isRolEnable(ua: LlaveUA) =
        ua.roleAssociated in arrayOf(GERENTE_REGION, GERENTE_ZONA, SOCIA_EMPRESARIA)

    private fun listarUploads() =
        listOf(postulantesRepository.sincronizar())
}
