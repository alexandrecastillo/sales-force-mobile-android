package biz.belcorp.salesforce.modules.developmentpath.features.sync

import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.DashboardSyncUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject

class DashboardSingleSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<DashboardSyncUseCase>()

    override val groups = listOf(SyncGroup.DEVELOPMENT_PATH_DASHBOARD)

    override suspend fun start() {
        try {
            publish(SyncState.Started)
            syncUseCase.sync()
            publish(SyncState.Updated)
        } catch (e: Exception) {
            publish(SyncState.Failed(e))
            throw e
        }
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.DEVELOPMENT_PATH_DASHBOARD_SYNC, state)
    }

}
