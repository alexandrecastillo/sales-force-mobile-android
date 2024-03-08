package biz.belcorp.salesforce.messaging.features.sync

import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.messaging.core.domain.usecases.SyncNotificationsUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class NotificationsSyncManager : SyncManager(), KoinComponent {

    private val syncNotificationsUseCase by inject<SyncNotificationsUseCase>()

    override val groups = listOf(SyncGroup.LOGIN)

    override suspend fun start() {
        try {
            publish(SyncState.Started)
            syncNotificationsUseCase.syncNotifications()
        } catch (e: Exception) {
            publish(SyncState.Failed(e))
            throw e
        }
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.MESSAGING_SYNC, state)
    }

}
