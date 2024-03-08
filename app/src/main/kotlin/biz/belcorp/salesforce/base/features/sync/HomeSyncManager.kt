package biz.belcorp.salesforce.base.features.sync

import biz.belcorp.salesforce.base.core.domain.usecases.sync.SyncUseCase
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class HomeSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SyncUseCase>()
    private val campaignsUseCase by inject<ObtenerCampaniasUseCase>()
    override val groups = listOf(
        SyncGroup.LOGIN,
        SyncGroup.HOME,
        SyncGroup.KPI_EDA // Temporal
    )

    override suspend fun start() {
        try {
            publish(SyncState.Started)
            GlobalScope.launch(Dispatchers.Default) {

                withContext(Dispatchers.IO) { startSync() }
                publish(SyncState.Updated)
            }
        } catch (e: Exception) {
            publish(SyncState.Failed(e))
            throw e
        }
    }

    private suspend fun startSync() {
        campaignsUseCase.syncIfNeeded()
        syncUseCase.sync()
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.HOME_SYNC, state)
    }

}
