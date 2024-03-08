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


internal class KpiSyncManager : SyncManager(), KoinComponent {

    private val kpiSyncUseCase by inject<KpiSyncUseCase>()

    override val groups = listOf(
        SyncGroup.LOGIN,
        SyncGroup.KPI_EDA,
        SyncGroup.HOME_FORCED_ON_DEMAND
    )

    override suspend fun start() {
        try {
            publish(SyncState.Started)
            kpiSyncUseCase.sync(KpiType.NEW_CYCLES)
            kpiSyncUseCase.sync(KpiType.SALE_ORDERS)
            kpiSyncUseCase.sync(KpiType.CAPITALIZATION)
            publish(SyncState.Updated)
        } catch (e: Exception) {
            publish(SyncState.Failed(e))
            throw e
        }
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.KPIS_KPIS_SYNC, state)
    }

}
