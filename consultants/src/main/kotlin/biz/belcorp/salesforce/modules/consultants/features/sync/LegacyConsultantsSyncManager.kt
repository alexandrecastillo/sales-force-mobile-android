package biz.belcorp.salesforce.modules.consultants.features.sync

import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.SyncUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class LegacyConsultantsSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SyncUseCase>()

    override val groups = listOf(
        SyncGroup.HOME_ON_LOGIN,
        SyncGroup.CONSULTANTS_LEGACY
    )

    override suspend fun start() {
        try {
            publish(SyncState.Started)
            startLegacySync()
        } catch (e: Exception) {
            publish(SyncState.Failed(e))
            throw e
        }
    }

    private suspend fun startLegacySync() {
        val syncType = syncUseCase.sync()
        if (syncType != SyncType.None) {
            publish(SyncState.Updated)
        }
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.CONSULTANTS_SYNC, state)
    }

}
