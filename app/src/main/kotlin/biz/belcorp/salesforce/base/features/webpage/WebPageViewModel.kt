package biz.belcorp.salesforce.base.features.webpage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.usecases.browser.GetWebUrlUseCase
import biz.belcorp.salesforce.core.domain.usecases.browser.WebTopic
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import kotlinx.coroutines.launch

class WebPageViewModel(
    private val getWebUrlUseCase: GetWebUrlUseCase
) : ViewModel() {

    val viewState: LiveData<WebPageViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<WebPageViewState>()

    fun getUrlByTopic(webTopic: WebTopic?) {
        viewModelScope.launch(handler) {
            io {
                val url = getWebUrlUseCase.getUrl(requireNotNull(webTopic))
                _viewState.postValue(WebPageViewState.LoadWebPage(url))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            Log.e(javaClass.name, message)
            _viewState.postValue(WebPageViewState.Failed)
        }
    }

}
