package biz.belcorp.salesforce.modules.digital.features.sync

import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.digital.core.domain.usecases.SyncDigitalInfoUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class DigitalSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SyncDigitalInfoUseCase>()

    override val groups = listOf(
        SyncGroup.HOME_ON_LOGIN,
        SyncGroup.HOME_FORCED_ON_DEMAND,
        SyncGroup.KPI_EDA // Temporal
    )

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
        LiveDataBus.publish(EventSubject.DIGITAL_SYNC, state)
    }

}
