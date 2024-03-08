package biz.belcorp.salesforce.base.core.domain.usecases.sync

import biz.belcorp.salesforce.base.core.domain.repository.options.OptionsRepository
import biz.belcorp.salesforce.core.domain.entities.people.SyncParams
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerSyncRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectorySyncRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.repository.ua.UaInfoRepository
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class SyncUseCase(
    private val optionsRepository: OptionsRepository,
    private val sessionRepository: SessionRepository,
    private val uaInfoRepository: UaInfoRepository,
    private val configRepository: ConfigurationRepository,
    private val managerDirectoryRepository: ManagerDirectorySyncRepository,
    private val businessPartnerSyncRepository: BusinessPartnerSyncRepository
) {

    private val session get() = requireNotNull(sessionRepository.getSession())

    suspend fun sync() {
        with(session) {
            coroutineScope {
                val deferredsSync = listOf(
                    async { optionsRepository.sync(countryIso, codigoRol, llaveUA) },
                    async { configRepository.sync(countryIso) },
                    async { uaInfoRepository.sync(countryIso, person) },
                    async { syncPeople() }
                )
                deferredsSync.awaitAll()
            }
        }
    }

    private suspend fun syncPeople(): Unit = with(session) {
        when {
            rol.isDV() || rol.isGR() -> managerDirectoryRepository.sync(getSyncParams())
            rol.isGZ() -> businessPartnerSyncRepository.sync(getSyncParams())
            else -> Unit
        }
    }

    private fun getSyncParams(): SyncParams = with(session) {
        return SyncParams(llaveUA, countryIso, campaign.codigo)
    }

}
