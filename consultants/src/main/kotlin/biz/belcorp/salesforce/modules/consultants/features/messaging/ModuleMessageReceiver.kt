package biz.belcorp.salesforce.modules.consultants.features.messaging

import android.content.Context
import biz.belcorp.salesforce.core.messaging.MessageReceiver
import biz.belcorp.salesforce.core.messaging.model.Payload
import biz.belcorp.salesforce.modules.consultants.features.sync.utils.startConsultantsPendingDebtSyncWorker
import biz.belcorp.salesforce.modules.consultants.features.sync.utils.startConsultantsSyncWorker
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class ModuleMessageReceiver : MessageReceiver, KoinComponent {

    private val context by inject<Context>()

    companion object {

        const val SUBTOPIC_CONSULTANT_LOAD_DATA = "CONSULTANT_LOAD_DATA"
        const val SUBTOPIC_CONSULTANT_PENDING_DEBT = "CONSULTANT_PENDING_DEBT"

    }

    override val subtopics: List<String>
        get() = listOf(
            SUBTOPIC_CONSULTANT_LOAD_DATA,
            SUBTOPIC_CONSULTANT_PENDING_DEBT
        )

    override fun onReceiveMessage(payload: Payload) {
        when (payload.subtopic) {
            SUBTOPIC_CONSULTANT_LOAD_DATA -> {
                context.startConsultantsSyncWorker()
            }
            SUBTOPIC_CONSULTANT_PENDING_DEBT -> {
                context.startConsultantsPendingDebtSyncWorker()
            }
        }
    }

}
