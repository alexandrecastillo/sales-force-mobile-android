package biz.belcorp.salesforce.messaging.core.domain.repository

import biz.belcorp.salesforce.messaging.core.domain.entities.Notification


interface NotificationsRepository {

    suspend fun saveNotifications(notification: Notification)
    suspend fun hasPendingNotificationByTopic(topic: String): Boolean
    suspend fun hasPendingNotifications(): Boolean
    suspend fun getNotifications(topic: String): List<Notification>
    suspend fun getNotification(code: Long): Notification
    suspend fun updateNotification(code: Long, user: String)
    suspend fun syncNotificationList(appId: Int, country: String, user: String)

}
