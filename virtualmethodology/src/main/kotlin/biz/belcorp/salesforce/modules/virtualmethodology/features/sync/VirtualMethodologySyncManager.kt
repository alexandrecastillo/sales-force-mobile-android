package biz.belcorp.salesforce.modules.virtualmethodology.features.sync

import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.usecases.SyncGroupSegUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class VirtualMethodologySyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SyncGroupSegUseCase>()

    override val groups = listOf(
        SyncGroup.LOGIN,
        SyncGroup.VIRTUAL_METHODOLOGY
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
        LiveDataBus.publish(EventSubject.VIRTUAL_METHODOLOGY_SYNC, state)
    }

}
