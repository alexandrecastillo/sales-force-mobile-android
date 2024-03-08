package biz.belcorp.salesforce.messaging.core.domain.usecases

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.differenceInDays
import biz.belcorp.salesforce.core.utils.differenceInExplicitDays
import biz.belcorp.salesforce.messaging.core.domain.constants.Topics
import biz.belcorp.salesforce.messaging.core.domain.entities.Notification
import biz.belcorp.salesforce.messaging.core.domain.entities.NotificationResponse
import biz.belcorp.salesforce.messaging.core.domain.repository.NotificationsRepository
import java.util.*

class GetNotificationUseCase(
    private val notificationsRepository: NotificationsRepository
) {

    suspend fun getNotifications(topic: String): NotificationResponse {
        val notifications = notificationsRepository.getNotifications(topic)
        val (hasNews, hasPostulants, hasRdd) = hasPendingNotificationsByTopic()
        val (today, others) = splitNotifications(notifications)

        return NotificationResponse(today, others).apply {
            hasPendingNewsNotifcations = hasNews
            hasPendingPostulantsNotifications = hasPostulants
            hasPendingRddNotifcations = hasRdd
        }
    }

    suspend fun getPendingNotifications(): Boolean {
        return notificationsRepository.hasPendingNotifications()
    }

    private fun splitNotifications(notifications: List<Notification>):
        Pair<List<Notification>, List<Notification>> {
        return notifications.sortedByDescending { it.date }.partition {
            Date().differenceInExplicitDays(Date(it.date)) == Constant.NUMBER_ZERO
        }
    }

    private suspend fun hasPendingNotificationsByTopic(): Triple<Boolean, Boolean, Boolean> {
        val rddEvent = notificationsRepository.hasPendingNotificationByTopic(Topics.RDD_EVENT)
        val rdd = notificationsRepository.hasPendingNotificationByTopic(Topics.RDD_RECOGNITION)
        val postulants = notificationsRepository.hasPendingNotificationByTopic(Topics.POSTULANTS)
        val news = notificationsRepository.hasPendingNotificationByTopic(Topics.NEWS)

        return Triple(news, postulants, rdd || rddEvent)
    }

    suspend fun getNotification(code: String): Notification {
        return notificationsRepository.getNotification(code.toLong())
    }

}
