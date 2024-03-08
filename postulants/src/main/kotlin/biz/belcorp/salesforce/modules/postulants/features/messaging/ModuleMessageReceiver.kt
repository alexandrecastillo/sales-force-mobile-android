package biz.belcorp.salesforce.modules.postulants.features.messaging

import android.content.Context
import biz.belcorp.salesforce.core.messaging.MessageReceiver
import biz.belcorp.salesforce.core.messaging.model.Payload
import biz.belcorp.salesforce.modules.postulants.features.sync.utils.startModuleSyncWorker
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class ModuleMessageReceiver : MessageReceiver, KoinComponent {

    private val context by inject<Context>()

    companion object {

        const val SUBTOPIC_UNETE = "POSTULANTS_LOAD_DATA"

    }

    override val subtopics: List<String>
        get() = listOf(SUBTOPIC_UNETE)

    override fun onReceiveMessage(payload: Payload) {
        when (payload.subtopic) {
            SUBTOPIC_UNETE -> run {
                context.startModuleSyncWorker()
            }
        }
    }

}
