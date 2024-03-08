package biz.belcorp.salesforce.messaging.features.list.view

import biz.belcorp.salesforce.messaging.features.list.model.NotificationContainer
import biz.belcorp.salesforce.messaging.features.list.model.NotificationModel

sealed class NotificationViewState :
    BaseNotificationViewState {
    class Success(val model: NotificationContainer) : NotificationViewState()
    class Failure(val message: String) : NotificationViewState()
    class Action(val item: NotificationModel) : NotificationViewState()
}
