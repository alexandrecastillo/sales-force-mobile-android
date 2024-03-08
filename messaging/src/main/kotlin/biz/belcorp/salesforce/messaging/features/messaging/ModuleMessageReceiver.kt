package biz.belcorp.salesforce.messaging.features.messaging

import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.messaging.MessageReceiver
import biz.belcorp.salesforce.core.messaging.model.Payload
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics.NEWS
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics.POSTULANTS
import biz.belcorp.salesforce.messaging.core.domain.entities.NewsNotification
import biz.belcorp.salesforce.messaging.core.domain.entities.Notification
import biz.belcorp.salesforce.messaging.core.domain.entities.PostulantsNotification
import biz.belcorp.salesforce.messaging.core.domain.usecases.SaveNotificationUseCase
import biz.belcorp.salesforce.messaging.features.messaging.helpers.NewsNotificationHelper
import biz.belcorp.salesforce.messaging.features.messaging.helpers.PostulantsNotificationHelper
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject


class ModuleMessageReceiver : MessageReceiver, KoinComponent {

    private val saveNotificationUseCase by inject<SaveNotificationUseCase>()
    private val newsNotificationHelper by inject<NewsNotificationHelper>()
    private val postulantsNotificationHelper by inject<PostulantsNotificationHelper>()
    private val notificationMapper by inject<NotificationMapper>()

    override val subtopics: List<String>
        get() = listOf(
            POSTULANTS,
            NEWS
        )

    override fun onReceiveMessage(payload: Payload) {
        GlobalScope.launch(handler) {
            val notification = notificationMapper.map(payload)
            saveNotification(notification)
            when (notification) {
                is PostulantsNotification -> postulantsNotificationHelper.manage(notification)
                is NewsNotification -> newsNotificationHelper.manage(notification)
            }
            publish(SyncState.Updated)
        }
    }

    private suspend fun saveNotification(notification: Notification) {
        withContext(Dispatchers.IO) {
            saveNotificationUseCase.saveNotifications(notification)
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        publish(SyncState.Failed(exception))
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.MESSAGING_SYNC, state)
    }

}
