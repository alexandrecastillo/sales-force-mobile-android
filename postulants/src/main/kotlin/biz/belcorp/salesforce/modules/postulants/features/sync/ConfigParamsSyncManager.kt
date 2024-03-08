package biz.belcorp.salesforce.modules.postulants.features.sync

import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.sync.SyncPostulantesUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class ConfigParamsSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SyncPostulantesUseCase>()

    override val groups = listOf(
        SyncGroup.LOGIN,
        SyncGroup.POSTULANTS
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
        syncUseCase.sync()
        publish(SyncState.Updated)
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.POSTULANTS_SYNC, state)
    }

}
