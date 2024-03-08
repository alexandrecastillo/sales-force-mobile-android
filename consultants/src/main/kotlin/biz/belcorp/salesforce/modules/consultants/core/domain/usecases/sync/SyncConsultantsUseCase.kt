package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync


import biz.belcorp.salesforce.core.domain.entities.people.SyncParams
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.consultant.ConsultantsSyncRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.removeHyphens

class SyncConsultantsUseCase(
    private val consultantsSyncRepository: ConsultantsSyncRepository,
    private val campaignsRepository: CampaniasRepository,
    private val sessionRepository: SessionRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun sync(isForced: Boolean = false, onlyModified: Boolean = false): SyncType {
        val ua = session.llaveUA.removeHyphens()
        return if (isRolEnable(ua)) {
            val country = session.countryIso
            val campaign = campaignsRepository.obtenerCampaniaActualSuspend(ua)
            val params = SyncParams(
                ua = ua,
                countryIso = country,
                campaign = campaign.codigo,
                phase = campaign.getPhase(),
                isForced = isForced,
                onlyModified = onlyModified
            )
            consultantsSyncRepository.sync(params)
        } else {
            SyncType.None
        }
    }

    private fun isRolEnable(ua: LlaveUA) =
        ua.roleAssociated in arrayOf(Rol.GERENTE_ZONA, Rol.SOCIA_EMPRESARIA)

}
