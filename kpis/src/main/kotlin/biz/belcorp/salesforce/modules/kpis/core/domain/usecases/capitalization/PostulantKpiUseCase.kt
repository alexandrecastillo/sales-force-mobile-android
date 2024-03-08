package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization

import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.utils.isEqual
import biz.belcorp.salesforce.core.utils.removeHyphens
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.PostulantKpi
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.PostulantsKpiRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.sync.SyncParams

class PostulantKpiUseCase(
    private val sessionRepository: SessionRepository,
    private val postulantRepository: PostulantsKpiRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun getPostulantsKpi(ua: LlaveUA): List<PostulantKpi> {
        val isParent = ua.isEqual(session.llaveUA)
        val syncParams = getSyncParams(ua, isParent)
        return when (postulantRepository.sync(syncParams)) {
            is SyncType.Updated -> postulantRepository.getPostulantsKpi(ua)
            else -> emptyList()
        }
    }

    private fun getSyncParams(ua: LlaveUA, hasToBeUpdated: Boolean) =
        SyncParams(
            ua.removeHyphens(),
            session.countryIso,
            CampaignRules.isBilling(session.campaign),
            isForced = true,
            hasToBeUpdated = hasToBeUpdated
        )

    fun isParent(ua: LlaveUA) = session.llaveUA.isEqual(ua)
}
