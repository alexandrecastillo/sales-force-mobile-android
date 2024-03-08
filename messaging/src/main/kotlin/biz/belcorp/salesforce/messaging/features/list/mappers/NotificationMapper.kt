package biz.belcorp.salesforce.messaging.features.list.mappers

import biz.belcorp.salesforce.core.utils.AppUri.ACTION_KEY
import biz.belcorp.salesforce.core.utils.AppUri.CODE_KEY
import biz.belcorp.salesforce.core.utils.calculateElapsedTime
import biz.belcorp.salesforce.messaging.R
import biz.belcorp.salesforce.messaging.core.domain.constants.NotificationType
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics
import biz.belcorp.salesforce.messaging.core.domain.entities.Notification
import biz.belcorp.salesforce.messaging.core.domain.entities.NotificationResponse
import biz.belcorp.salesforce.messaging.core.domain.entities.RddRecognitionNotification
import biz.belcorp.salesforce.messaging.features.list.model.NotificationContainer
import biz.belcorp.salesforce.messaging.features.list.model.NotificationModel
import biz.belcorp.salesforce.messaging.features.list.view.NotificationTextResolver

class NotificationMapper(private val notificationTextResolver: NotificationTextResolver) {

    fun map(response: NotificationResponse): NotificationContainer = with(response) {
        val today = todayNotifications.map { map(it) }
        val others = otherNotifications.map { map(it) }
        return NotificationContainer(
            today,
            others
        ).apply {
            hasPendingNewsNotifications = response.hasPendingNewsNotifcations
            hasPendingPostulantsNotifications = response.hasPendingPostulantsNotifications
            hasPendingRddNotifications = response.hasPendingRddNotifcations
        }
    }

    fun map(notification: Notification): NotificationModel = with(notification) {
        val type = createNotificationType(topic)
        val icon = createNotificationIcon(type)
        return NotificationModel(
            code = id,
            message = message,
            topic = topic,
            type = createNotificationType(topic),
            icon = icon.first,
            iconColor = R.color.white,
            backgroundColor = icon.second,
            elapsedTime = createElapsedTime(date),
            seen = seen,
            uriParams = createUriParams()
        )
    }

    private fun createNotificationType(topic: String): Int {
        return when (topic) {
            Topics.RDD_EVENT,
            Topics.RDD_RECOGNITION -> NotificationType.RDD
            Topics.POSTULANTS -> NotificationType.POSTULANTS
            Topics.NEWS -> NotificationType.NEWS
            else -> NotificationType.NONE
        }
    }

    private fun createNotificationIcon(@NotificationType type: Int): Pair<Int, Int> {
        return when (type) {
            NotificationType.RDD -> Pair(R.drawable.ic_rdd, R.color.colorIconRdd)
            NotificationType.NEWS -> Pair(R.drawable.ic_news, R.color.colorIconNews)
            NotificationType.POSTULANTS -> Pair(
                R.drawable.ic_postulants,
                R.color.colorIconPostulants
            )
            else -> Pair(R.drawable.icon_circle, R.color.white)
        }
    }

    private fun createElapsedTime(time: Long): String {
        val elapsedTime = calculateElapsedTime(time)
        return notificationTextResolver.formatElapsedTime(elapsedTime)
    }

    private fun Notification.createUriParams(): HashMap<String, String?> {
        return when (this) {
            is RddRecognitionNotification -> {
                hashMapOf(
                    ACTION_KEY to topic,
                    CODE_KEY to data?.id?.toString()
                )
            }
            else -> {
                hashMapOf(
                    ACTION_KEY to topic,
                    CODE_KEY to id.toString()
                )
            }
        }
    }

}
