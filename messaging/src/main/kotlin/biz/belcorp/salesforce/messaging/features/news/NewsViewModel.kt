package biz.belcorp.salesforce.messaging.features.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.messaging.core.domain.entities.NewsNotification
import biz.belcorp.salesforce.messaging.core.domain.usecases.GetNotificationUseCase
import biz.belcorp.salesforce.messaging.features.news.mapper.NewsMapper
import kotlinx.coroutines.launch


class NewsViewModel(
    private val getNotificationUseCase: GetNotificationUseCase,
    private val newsMapper: NewsMapper
) : ViewModel() {

    val viewState: LiveData<NewsViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<NewsViewState>()

    fun loadDetail(code: String) {
        viewModelScope.launch(handler) {
            io {
                val notification = getNotificationUseCase.getNotification(code) as? NewsNotification
                val model = newsMapper.map(notification ?: return@io)
                _viewState.postValue(NewsViewState.ShowDetail(model))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
