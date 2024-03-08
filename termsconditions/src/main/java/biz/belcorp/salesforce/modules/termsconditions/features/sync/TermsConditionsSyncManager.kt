package biz.belcorp.salesforce.modules.termsconditions.features.sync

import biz.belcorp.salesforce.core.domain.usecases.terms.SyncTermsConditionsUseCase
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import org.koin.core.KoinComponent
import org.koin.core.inject

class TermsConditionsSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SyncTermsConditionsUseCase>()

    override val groups = listOf(
        SyncGroup.TERMS_CONDITIONS,
        SyncGroup.KPI_EDA,
        SyncGroup.LOGIN,
        SyncGroup.HOME,
        SyncGroup.TERMS_CONDITIONS
    )

    override suspend fun start() {
        try {
            publish(SyncState.Started)
            syncUseCase.sync()
            syncUseCase.syncApprovedTerms()
            publish(SyncState.Updated)
        } catch (e: Exception) {
            publish(SyncState.Failed(e))
            throw e
        }
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.POLITICS_TERMS_CONDITIONS_SYNC, state)
    }

}
