package biz.belcorp.salesforce.modules.developmentpath.features.messaging


import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.messaging.MessageReceiver
import biz.belcorp.salesforce.core.messaging.model.Payload
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics.RDD_EVENT
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics.RDD_RECOGNITION
import biz.belcorp.salesforce.messaging.core.domain.entities.RddEventNotification
import biz.belcorp.salesforce.messaging.core.domain.entities.RddRecognitionNotification
import biz.belcorp.salesforce.messaging.core.domain.usecases.SaveNotificationUseCase
import biz.belcorp.salesforce.messaging.features.messaging.NotificationMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventosCambiadosRespuesta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.SolicitarEventosCambiadosUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.SaveRecognitionUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.messaging.helpers.RddNotificationHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class ModuleMessageReceiver : MessageReceiver, KoinComponent {

    private val saveRecognitionUseCase by inject<SaveRecognitionUseCase>()
    private val saveNotificationUseCase by inject<SaveNotificationUseCase>()
    private val rddNotificationHelper by inject<RddNotificationHelper>()
    private val eventsChangedUseCase by inject<SolicitarEventosCambiadosUseCase>()
    private val notificationMapper by inject<NotificationMapper>()

    override val subtopics: List<String>
        get() = listOf(
            RDD_RECOGNITION,
            RDD_EVENT
        )

    override fun onReceiveMessage(payload: Payload) {
        GlobalScope.launch(handler) {
            when (val notification = notificationMapper.map(payload)) {
                is RddEventNotification -> syncEvents()
                is RddRecognitionNotification -> manageRecognition(notification)
            }
        }
    }

    private suspend fun manageRecognition(notification: RddRecognitionNotification) {
        saveNotificationUseCase.saveNotifications(notification)
        saveRecognitionUseCase.saveRecogniton(notification)
        rddNotificationHelper.manage(notification)
        publish(SyncState.Updated)
    }

    private suspend fun syncEvents() {
        io {
            suspendCancellableCoroutine<EventosCambiadosRespuesta> { cont ->
                eventsChangedUseCase.solicitar(object :
                    BaseSingleObserver<EventosCambiadosRespuesta>() {
                    override fun onSuccess(t: EventosCambiadosRespuesta) {
                        cont.resume(t)
                    }

                    override fun onError(exception: Throwable) {
                        cont.resumeWithException(exception)
                    }
                })
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        publish(SyncState.Failed(exception))
    }

    private fun publish(state: SyncState) {
        LiveDataBus.publish(EventSubject.MESSAGING_SYNC, state)
    }

}
