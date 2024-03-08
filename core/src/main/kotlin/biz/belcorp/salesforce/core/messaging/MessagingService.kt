package biz.belcorp.salesforce.core.messaging

import biz.belcorp.salesforce.core.domain.usecases.device.UpdateTokenUseCase
import biz.belcorp.salesforce.core.domain.usecases.firebase.ManageTopicsUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.messaging.model.Payload
import biz.belcorp.salesforce.core.utils.injectAll
import biz.belcorp.salesforce.core.utils.jsonData
import biz.belcorp.salesforce.core.utils.notification.NotificationDataParser
import biz.belcorp.salesforce.core.utils.safeJsonParse
import biz.belcorp.salesforce.core.utils.topic
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject


class MessagingService : FirebaseMessagingService(), KoinComponent {

    private val session by inject<ObtenerSesionUseCase>()
    private val messageReceivers by injectAll<MessageReceiver>()
    private val saveNewTokenUseCase by inject<UpdateTokenUseCase>()
    private val manageTopicsUseCase by inject<ManageTopicsUseCase>()

    override fun onMessageReceived(message: RemoteMessage) {

        if (!session.esSesionActiva()) return

        val topic = message.topic ?: return
        val data = message.jsonData ?: return

        val payload = getPayload(topic, data)

        processPayload(payload)
    }

    private fun getPayload(topic: String, data: String): Payload {
        val payload = safeJsonParse(Payload.serializer(), data) ?: Payload()
        if (payload.subtopic == null) {
            val cleanData = payload.data?.let { NotificationDataParser.parse(it) }
            payload.subtopic = cleanData?.topic ?: topic
            payload.data = cleanData?.data ?: data
            payload.id = cleanData?.id
        }
        return payload
    }

    private fun processPayload(payload: Payload) {
        messageReceivers
            .filter { payload.subtopic in it.subtopics }
            .forEach {
                it.onReceiveMessage(payload)
            }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        GlobalScope.launch(handler) {
            if (session.esSesionActiva()) {
                manageTopicsUseCase.subscribeTopics()
            }
            saveNewTokenUseCase.updateToken(token)
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
