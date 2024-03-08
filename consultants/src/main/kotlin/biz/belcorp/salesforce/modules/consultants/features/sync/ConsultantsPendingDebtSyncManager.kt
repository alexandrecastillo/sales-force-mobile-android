package biz.belcorp.salesforce.modules.consultants.features.sync

import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.SyncConsultantsUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class ConsultantsPendingDebtSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SyncConsultantsUseCase>()

    override val groups = listOf(
        SyncGroup.CONSULTANTS_PENDING_DEBT
    )

    override suspend fun start() {
        try {
            publish(SyncState.Started)
            syncUseCase.sync(isForced = true, onlyModified = true)
            publish(SyncState.Updated)
        } catch (e: Exception) {
            publish(SyncState.Failed(e))
            throw e
        }
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.CONSULTANTS_SYNC, state)
    }

}
