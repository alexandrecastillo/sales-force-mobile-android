package biz.belcorp.salesforce.modules.kpis.features.sync

import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.sync.KpiSyncUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


internal class CollectionSyncManager : SyncManager(), KoinComponent {

    private val kpiSyncUseCase by inject<KpiSyncUseCase>()

    override val groups = listOf(
        SyncGroup.LOGIN,
        SyncGroup.HOME_FORCED_ON_DEMAND,
        SyncGroup.COLLECTION_EDA
    )

    override suspend fun start() {
        try {
            publish(SyncState.Started)
            kpiSyncUseCase.sync(KpiType.COLLECTION)
            publish(SyncState.Updated)
        } catch (e: Exception) {
            publish(SyncState.Failed(e))
            throw e
        }
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.KPIS_COLLECTION_SYNC, state)
    }

}
