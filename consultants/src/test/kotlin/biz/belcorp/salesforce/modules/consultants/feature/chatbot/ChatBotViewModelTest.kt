package biz.belcorp.salesforce.modules.consultants.feature.chatbot

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.chatbot.GetChatBotUrlUseCase
import biz.belcorp.salesforce.modules.consultants.features.chatbot.ChatBotTextResolver
import biz.belcorp.salesforce.modules.consultants.features.chatbot.ChatBotViewModel
import biz.belcorp.salesforce.modules.consultants.features.chatbot.ChatBotViewState
import biz.belcorp.salesforce.modules.consultants.utils.ChatBotMock.getSession
import biz.belcorp.salesforce.modules.consultants.utils.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class ChatBotViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val useCaseMock = mockk<GetChatBotUrlUseCase>()
    private val getSessionUseCaseMock = mockk<ObtenerSesionUseCase>()
    private val textResolver = mockk<ChatBotTextResolver>(relaxed = true)

    private lateinit var chatBotViewModel: ChatBotViewModel

    private val observer = mockk<Observer<ChatBotViewState>>(relaxed = true)

    @Before
    fun setup() {
        chatBotViewModel = ChatBotViewModel(useCaseMock, getSessionUseCaseMock, textResolver)
        chatBotViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `get chat bot url with success`() {
        val randomString = UUID.randomUUID().toString()
        coEvery { useCaseMock.getChatBotUrl() } returns randomString
        coEvery { getSessionUseCaseMock.obtener() } returns getSession()
        coEvery { textResolver.buildChatBotUrl(getSession(), randomString) } returns randomString
        chatBotViewModel.getChatBotUrl()
        coVerify { useCaseMock.getChatBotUrl() }
        chatBotViewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<ChatBotViewState.ChatBotSuccess>()) }
    }

    @Test
    fun `throws exception when get chat bot url`() {
        val randomString = UUID.randomUUID().toString()
        coEvery { useCaseMock.getChatBotUrl() } throws Exception()
        coEvery { getSessionUseCaseMock.obtener() } returns getSession()
        coEvery { textResolver.buildChatBotUrl(getSession(), randomString) } returns randomString
        chatBotViewModel.getChatBotUrl()
        coVerify { useCaseMock.getChatBotUrl() }
        chatBotViewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<ChatBotViewState.ChatBotError>()) }
    }
}
