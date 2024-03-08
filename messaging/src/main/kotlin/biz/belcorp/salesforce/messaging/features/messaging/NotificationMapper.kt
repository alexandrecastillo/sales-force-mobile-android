package biz.belcorp.salesforce.messaging.features.messaging

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.messaging.model.Payload
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics.NEWS
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics.POSTULANTS
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics.RDD_EVENT
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics.RDD_RECOGNITION
import biz.belcorp.salesforce.messaging.core.domain.entities.*


class NotificationMapper {

    companion object {

        private const val SEEN_VALUE = false
    }

    fun map(payload: Payload): Notification {
        return when (payload.subtopic) {
            RDD_RECOGNITION -> RddRecognitionNotification().from(payload)
            RDD_EVENT -> RddEventNotification().from(payload)
            POSTULANTS -> PostulantsNotification().from(payload)
            NEWS -> NewsNotification().from(payload)
            else -> throw Exception("Unknown Notification Type")
        }
    }

    private fun Notification.from(payload: Payload) = apply {
        id = payload.id ?: Constant.NUMBER_ZERO.toLong()
        topic = payload.subtopic.orEmpty()
        title = payload.title.orEmpty()
        message = payload.message.orEmpty()
        dataString = payload.data ?: Constant.EMPTY_OBJECT
        date = System.currentTimeMillis()
        seen = SEEN_VALUE
    }

}
