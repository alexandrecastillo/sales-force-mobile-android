package biz.belcorp.salesforce.modules.developmentmaterial.features.sync

import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.usecases.SyncDocumentsUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class DevelopmentMaterialSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SyncDocumentsUseCase>()

    override val groups = listOf(
        SyncGroup.LOGIN,
        SyncGroup.DEVELOPMENT_MATERIAL
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
        LiveDataBus.publish(EventSubject.DEVELOPMENT_MATERIAL_SYNC, state)
    }

}
