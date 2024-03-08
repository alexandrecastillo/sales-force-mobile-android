package biz.belcorp.salesforce.messaging.core.domain.usecases

import biz.belcorp.salesforce.messaging.core.domain.entities.Notification
import biz.belcorp.salesforce.messaging.core.domain.repository.NotificationsRepository

class SaveNotificationUseCase(
    private val notificationsRepository: NotificationsRepository
) {

    suspend fun saveNotifications(notification: Notification) {
        notificationsRepository.saveNotifications(notification)
    }

}
