package biz.belcorp.salesforce.modules.consultants.features.chatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.io
import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.chatbot.GetChatBotUrlUseCase
import kotlinx.coroutines.launch

class ChatBotViewModel(
    private val chatBotUrlUseCase: GetChatBotUrlUseCase,
    private val getSessionUseCase: ObtenerSesionUseCase,
    private val chatBotTextResolver: ChatBotTextResolver
) : ViewModel() {

    private val session get() = getSessionUseCase.obtener()

    val viewState: LiveData<ChatBotViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<ChatBotViewState>()

    fun getChatBotUrl() {
        viewModelScope.launch(handler) {
            io {
                val url = chatBotTextResolver.buildChatBotUrl(session, chatBotUrlUseCase.getChatBotUrl())
                _viewState.postValue(ChatBotViewState.ChatBotSuccess(url))
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        ui {
            val message = exception.message.orEmpty()
            _viewState.value = ChatBotViewState.ChatBotError(message)
        }
    }
}

