package biz.belcorp.salesforce.core.features.messaging

import android.content.Context
import biz.belcorp.salesforce.core.domain.usecases.firebase.FetchRemoteConfigUseCase
import biz.belcorp.salesforce.core.features.sync.utils.startCampaignsSyncWorker
import biz.belcorp.salesforce.core.features.sync.utils.startSearchFiltersSyncWorker
import biz.belcorp.salesforce.core.messaging.MessageReceiver
import biz.belcorp.salesforce.core.messaging.model.Payload
import biz.belcorp.salesforce.core.utils.io
import org.koin.core.KoinComponent
import org.koin.core.inject


class ModuleMessageReceiver : MessageReceiver, KoinComponent {

    private val context by inject<Context>()
    private val fetchRemoteConfigUseCase by inject<FetchRemoteConfigUseCase>()

    companion object {

        const val SUBTOPIC_PUSH_RC = "PUSH_RC"
        const val SUBTOPIC_CAMPAIGNS = "CAMPAIGNS_UPDATE"
        const val SUBTOPIC_SEARCH_FILTERS = "SEARCH_FILTERS_UPDATE"

    }

    override val subtopics: List<String>
        get() = listOf(SUBTOPIC_PUSH_RC, SUBTOPIC_CAMPAIGNS, SUBTOPIC_SEARCH_FILTERS)

    override fun onReceiveMessage(payload: Payload) {
        when (payload.subtopic) {
            SUBTOPIC_PUSH_RC -> fetchRemoteConfig()
            SUBTOPIC_CAMPAIGNS -> context.startCampaignsSyncWorker()
            SUBTOPIC_SEARCH_FILTERS -> context.startSearchFiltersSyncWorker()
        }
    }

    private fun fetchRemoteConfig() {
        io { fetchRemoteConfigUseCase.fetchConfig() }
    }

}
