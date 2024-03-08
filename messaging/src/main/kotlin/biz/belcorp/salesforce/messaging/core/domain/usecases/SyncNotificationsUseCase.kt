package biz.belcorp.salesforce.messaging.core.domain.usecases

import biz.belcorp.salesforce.core.constants.Constant.APP_ID
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.messaging.core.domain.repository.NotificationsRepository


class SyncNotificationsUseCase(
    private val repository: NotificationsRepository,
    private val sessionRepository: SessionRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }

    suspend fun syncNotifications() {
        repository.syncNotificationList(APP_ID, session.countryIso, session.codeByRole)
    }

}
