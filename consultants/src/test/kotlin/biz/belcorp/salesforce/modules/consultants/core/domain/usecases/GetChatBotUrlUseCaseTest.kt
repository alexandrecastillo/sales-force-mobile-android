@file:Suppress("EXPERIMENTAL_API_USAGE")
package biz.belcorp.salesforce.modules.consultants.core.domain.usecases

import biz.belcorp.salesforce.modules.consultants.core.domain.repository.chatbot.ChatBotRepository
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.chatbot.GetChatBotUrlUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.util.*

class GetChatBotUrlUseCaseTest {

    private val chatBotRepository = mockk<ChatBotRepository>()
    private lateinit var useCase: GetChatBotUrlUseCase

    @Before
    fun setup() {
        useCase = GetChatBotUrlUseCase(chatBotRepository)
    }

    @Test
    fun `should find a chat bot url`() = runBlockingTest {
        val randomString = UUID.randomUUID().toString()
        coEvery { chatBotRepository.getChatBotUrl() } returns randomString
        useCase.getChatBotUrl()
        coVerify {
            chatBotRepository.getChatBotUrl()
        }
    }

    @Test(expected = Exception::class)
    fun `shouldn't find a chat bot url`() = runBlockingTest {
        coEvery { chatBotRepository.getChatBotUrl() } throws Exception()
        useCase.getChatBotUrl()
        coVerify {
            chatBotRepository.getChatBotUrl()
        }
    }
}
