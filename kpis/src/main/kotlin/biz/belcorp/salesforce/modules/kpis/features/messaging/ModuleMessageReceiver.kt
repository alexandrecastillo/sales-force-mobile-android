package biz.belcorp.salesforce.modules.kpis.features.messaging

import android.content.Context
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.messaging.MessageReceiver
import biz.belcorp.salesforce.core.messaging.model.Payload
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.modules.kpis.features.sync.utils.startCollectionSyncWorker
import biz.belcorp.salesforce.modules.kpis.features.sync.utils.startKpiSyncWorker
import org.koin.core.KoinComponent
import org.koin.core.inject


internal class ModuleMessageReceiver : MessageReceiver, KoinComponent {

    private val context by inject<Context>()

    companion object {

        const val SUBTOPIC_COLLECTION = "COLLECTION_LOAD_DATA"
        const val SUBTOPIC_KPI = "KPI_LOAD_DATA"

    }

    override val subtopics: List<String>
        get() = listOf(SUBTOPIC_COLLECTION, SUBTOPIC_KPI)

    override fun onReceiveMessage(payload: Payload) {
        Logger.d("EDA: " + payload.subtopic, payload.data ?: EMPTY_STRING)
        when (payload.subtopic) {
            SUBTOPIC_COLLECTION -> {
                context.startCollectionSyncWorker()
            }
            SUBTOPIC_KPI -> {
                context.startKpiSyncWorker()
            }
        }
    }

}
