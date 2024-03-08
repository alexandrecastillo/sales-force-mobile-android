package biz.belcorp.salesforce.messaging.core.data.repository.data

import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.messaging.core.data.repository.NotificationMapper
import biz.belcorp.salesforce.messaging.core.data.repository.cloud.NotificationCloudStore
import biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto.NotificationListQuery
import biz.belcorp.salesforce.messaging.core.data.repository.cloud.dto.NotificationSeenQuery
import biz.belcorp.salesforce.messaging.core.domain.entities.Notification
import biz.belcorp.salesforce.messaging.core.domain.repository.NotificationsRepository


class NotificationsDataRepository(
    private val cloudStore: NotificationCloudStore,
    private val dataStore: NotificationsDataStore,
    private val mapper: NotificationMapper,
    private val configSharedPreferences: ConfigSharedPreferences
) : NotificationsRepository {

    override suspend fun saveNotifications(notification: Notification) {
        dataStore.saveNotification(mapper.map(notification))
    }

    override suspend fun getNotifications(topic: String): List<Notification> {
        val data = dataStore.getNotifications(topic)
        return data.map { mapper.reverseMap(it) }
    }

    override suspend fun getNotification(code: Long): Notification {
        val data = dataStore.getNotification(code)
        return mapper.reverseMap(requireNotNull(data))
    }

    override suspend fun hasPendingNotificationByTopic(topic: String): Boolean {
        return dataStore.hasPendingNotificationsByTopic(topic)
    }

    override suspend fun hasPendingNotifications(): Boolean {
        return dataStore.hasPendingNotifications()
    }

    override suspend fun updateNotification(code: Long, user: String) {
        val query = NotificationSeenQuery(code, configSharedPreferences.deviceId, user)
        cloudStore.updateNotificationSeen(query)
        dataStore.updateNotification(code)
    }

    override suspend fun syncNotificationList(appId: Int, country: String, user: String) {
        val query = NotificationListQuery(appId, country, user)
        val list = cloudStore.getNotificationList(query)
        dataStore.saveNotifications(mapper.map(list))
    }

}
