package biz.belcorp.salesforce.messaging.features.list.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.messaging.core.domain.usecases.GetNotificationUseCase
import biz.belcorp.salesforce.messaging.core.domain.usecases.UpdateNotificationUseCase
import biz.belcorp.salesforce.messaging.features.list.mappers.NotificationMapper
import biz.belcorp.salesforce.messaging.features.list.model.NotificationModel
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getNotificationUseCase: GetNotificationUseCase,
    private val updateNotificationUseCase: UpdateNotificationUseCase,
    private val mapper: NotificationMapper
) : ViewModel() {

    val viewState: LiveData<BaseNotificationViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<BaseNotificationViewState>()

    fun getNotifications(topic: String) {
        viewModelScope.launch(handler) {
            val data = getNotificationUseCase.getNotifications(topic)
            val model = mapper.map(data)
            _viewState.postValue(
                NotificationViewState.Success(
                    model
                )
            )
        }
    }

    fun updateNotification(item: NotificationModel) {
        viewModelScope.launch {
            try {
                updateNotificationUseCase.updateNotification(item.code)
                _viewState.postValue(NotificationViewState.Action(item))
            } catch (e: Exception) {
                _viewState.postValue(NotificationViewState.Action(item))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            _viewState.value =
                NotificationViewState.Failure(
                    exception.message.orEmpty()
                )
        }
    }

}
