package biz.belcorp.salesforce.messaging.core.data.repository

import biz.belcorp.salesforce.core.entities.NotificationEntity
import biz.belcorp.salesforce.core.utils.toDateWithT
import biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto.NotificationListResponse
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics
import biz.belcorp.salesforce.messaging.core.domain.entities.*


class NotificationMapper {

    fun map(list: List<NotificationListResponse.Notification>) =
        list.map { map(it) }

    fun map(notification: NotificationListResponse.Notification) = NotificationEntity(
        code = notification.code.toLong(),
        topic = notification.subtopic,
        title = notification.title,
        message = notification.message,
        data = notification.data,
        date = notification.date.toDateWithT()?.time ?: System.currentTimeMillis(),
        seen = notification.seen
    )

    fun map(notification: Notification) = NotificationEntity(
        code = notification.id,
        topic = notification.topic,
        title = notification.title,
        message = notification.message,
        data = notification.dataString,
        date = notification.date,
        seen = notification.seen
    )

    fun reverseMap(entity: NotificationEntity): Notification = with(entity) {
        return when (entity.topic) {
            Topics.RDD_RECOGNITION -> RddRecognitionNotification().convert(this)
            Topics.NEWS -> NewsNotification().convert(this)
            Topics.POSTULANTS -> PostulantsNotification().convert(this)
            Topics.RDD_EVENT -> RddEventNotification().convert(this)
            else -> throw Exception("Unknown topic type")
        }
    }

    private fun Notification.convert(entity: NotificationEntity) = apply {
        id = entity.code
        topic = entity.topic
        title = entity.title
        message = entity.message
        dataString = entity.data
        date = entity.date
        seen = entity.seen
    }

}
