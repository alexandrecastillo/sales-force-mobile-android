package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync

import biz.belcorp.salesforce.core.domain.entities.people.SyncParams
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerSyncRepository
import biz.belcorp.salesforce.core.domain.repository.consultant.ConsultantsSyncRepository
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectorySyncRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.isSE

class DashboardSyncUseCase(
    private val sessionRepository: SessionRepository,
    private val managersSyncRepository: ManagerDirectorySyncRepository,
    private val businessPartnerSyncRepository: BusinessPartnerSyncRepository,
    private val consultantsSyncRepository: ConsultantsSyncRepository?
) {

    private val session get() = requireNotNull(sessionRepository.getSession())

    suspend fun sync() {
        syncPeople()
    }

    private suspend fun syncPeople(): Unit = with(session) {
        val params = SyncParams(llaveUA, countryIso, campaign.codigo, campaign.getPhase())
        when {
            rol.isDV() -> {
                managersSyncRepository.sync(params)
            }
            rol.isGR() -> {
                managersSyncRepository.sync(params)
                businessPartnerSyncRepository.sync(params)
            }
            rol.isGZ() -> {
                businessPartnerSyncRepository.sync(params)
                consultantsSyncRepository?.sync(params)
            }
            rol.isSE() -> {
                consultantsSyncRepository?.sync(params)
            }
        }
    }

}
