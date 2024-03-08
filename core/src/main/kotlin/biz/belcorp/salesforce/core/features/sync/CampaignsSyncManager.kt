package biz.belcorp.salesforce.core.features.sync

import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class CampaignsSyncManager : SyncManager(), KoinComponent {

    private val useCase by inject<ObtenerCampaniasUseCase>()

    override val groups = listOf(
        SyncGroup.LOGIN,
        SyncGroup.CAMPAIGNS_EDA,
        SyncGroup.COLLECTION_EDA,
        SyncGroup.KPI_EDA,
        SyncGroup.CONSULTANTS_EDA,
        SyncGroup.HOME_FORCED_ON_DEMAND
    )

    override suspend fun start() {
        try {
            publish(SyncState.Started)
            startSync()
        } catch (e: Exception) {
            publish(SyncState.Failed(e))
            throw e
        }
    }

    private suspend fun startSync() {
        useCase.sync()
        publish(SyncState.Updated)
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.CAMPAIGNS_SYNC, state)
    }

}
