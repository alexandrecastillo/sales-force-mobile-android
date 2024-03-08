package biz.belcorp.salesforce.modules.termsconditions.features.messaging

import android.content.Context
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.messaging.MessageReceiver
import biz.belcorp.salesforce.core.messaging.model.Payload
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.modules.termsconditions.features.sync.utils.startTermsSyncWorker
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class ModuleMessageReceiver : MessageReceiver, KoinComponent {

    private val context by inject<Context>()

    companion object {
        const val SUBTOPIC_TERMS = "TERMS_CONDITION"
    }

    override val subtopics: List<String> get() = listOf(SUBTOPIC_TERMS)

    override fun onReceiveMessage(payload: Payload) {
        Logger.d("EDA: " + payload.subtopic, payload.data.orEmpty())
        when (payload.subtopic) {
            SUBTOPIC_TERMS -> {
                context.startTermsSyncWorker()
            }
        }
    }

}
