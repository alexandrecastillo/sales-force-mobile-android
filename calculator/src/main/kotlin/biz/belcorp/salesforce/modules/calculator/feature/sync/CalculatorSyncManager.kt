package biz.belcorp.salesforce.modules.calculator.feature.sync

import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.SyncUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject

class CalculatorSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SyncUseCase>()

    override val groups = listOf(SyncGroup.LOGIN, SyncGroup.HOME_FORCED_ON_DEMAND)

    override suspend fun start() {
        try {
            startLegacySync()
        } catch (e: Exception) {
            publish(SyncState.Failed(e))
            throw e
        }
    }

    private suspend fun startLegacySync() {
        syncUseCase.sync()
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.CALCULATOR_SYNC, state)
    }

}
