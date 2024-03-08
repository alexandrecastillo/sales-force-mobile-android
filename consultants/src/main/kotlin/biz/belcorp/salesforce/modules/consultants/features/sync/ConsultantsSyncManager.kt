package biz.belcorp.salesforce.modules.consultants.features.sync

import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.SyncConsultantsUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class ConsultantsSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SyncConsultantsUseCase>()

    override val groups = listOf(
        SyncGroup.HOME_ON_LOGIN,
        SyncGroup.HOME_FORCED_ON_DEMAND,
        SyncGroup.CONSULTANTS_EDA
    )

    override suspend fun start() {
        try {
            publish(SyncState.Started)
            syncUseCase.sync(isForced = isForced())
            publish(SyncState.Updated)
        } catch (e: Exception) {
            publish(SyncState.Failed(e))
            throw e
        }
    }

    private fun isForced() = let {
        currentSyncGroup == SyncGroup.HOME_FORCED_ON_DEMAND ||
        currentSyncGroup == SyncGroup.CONSULTANTS_EDA
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.CONSULTANTS_SYNC, state)
    }

}
