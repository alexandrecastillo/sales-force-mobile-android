package biz.belcorp.salesforce.messaging.core.data.repository.data

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.db.objectbox.ObjectBox.boxStore
import biz.belcorp.salesforce.core.entities.NotificationEntity
import biz.belcorp.salesforce.core.entities.NotificationEntity_
import biz.belcorp.salesforce.core.utils.contains
import biz.belcorp.salesforce.core.utils.deleteAndInsert
import biz.belcorp.salesforce.core.utils.equal
import io.objectbox.kotlin.boxFor


class NotificationsDataStore {

    fun saveNotification(notificationEntity: NotificationEntity) {
        boxStore.boxFor<NotificationEntity>()
            .put(notificationEntity)
    }

    fun saveNotifications(notifications: List<NotificationEntity>) {
        boxStore.boxFor<NotificationEntity>()
            .deleteAndInsert(notifications)
    }

    fun getNotifications(topic: String): List<NotificationEntity> {
        return boxStore.boxFor<NotificationEntity>()
            .query()
            .contains(NotificationEntity_.topic, topic)
            .build()
            .find()
    }

    fun getNotification(code: Long): NotificationEntity? {
        return boxStore.boxFor<NotificationEntity>()
            .query()
            .equal(NotificationEntity_.code, code)
            .build()
            .findFirst()
    }

    fun hasPendingNotificationsByTopic(topic: String): Boolean {
        val totalNotifications = boxStore.boxFor<NotificationEntity>().query()
            .equal(NotificationEntity_.topic, topic).and()
            .equal(NotificationEntity_.seen, false)
            .build()
            .count()
        return totalNotifications > Constant.NUMBER_ZERO
    }

    fun hasPendingNotifications(): Boolean {
        val totalNotifications = boxStore.boxFor<NotificationEntity>().query()
            .equal(NotificationEntity_.seen, false)
            .build()
            .count()
        return totalNotifications > Constant.NUMBER_ZERO
    }

    fun updateNotification(code: Long) {
        val box = boxStore.boxFor<NotificationEntity>()
        val entity = box.query().equal(NotificationEntity_.code, code).build().findFirst()
        entity?.let {
            it.seen = true
            box.put(it)
        }
    }
}
