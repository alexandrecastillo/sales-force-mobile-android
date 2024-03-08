package biz.belcorp.salesforce.messaging.core.domain.usecases

import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.messaging.core.domain.repository.NotificationsRepository

class UpdateNotificationUseCase(
    private val notificationsRepository: NotificationsRepository,
    private val sessionRepository: SessionRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun updateNotification(code: Long) {
        return notificationsRepository.updateNotification(code, session.codigoUsuario)
    }
}
