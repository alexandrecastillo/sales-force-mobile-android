@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.consultants.core.data
import biz.belcorp.salesforce.modules.consultants.core.data.repository.chatbot.ChatBotDataRepository
import biz.belcorp.salesforce.modules.consultants.core.data.repository.chatbot.cloud.ChatBotCloudStore
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.chatbot.ChatBotRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.util.*

class ChatBotDataRepositoryTest {

    private val chatBotCloudStore = mockk<ChatBotCloudStore>()

    private lateinit var chatBotDataRepository: ChatBotRepository

    @Before
    fun setup() {
        chatBotDataRepository = ChatBotDataRepository(chatBotCloudStore)
    }

    @Test
    fun `should find a chat bot url`() = runBlockingTest {
        val randomString = UUID.randomUUID().toString()
        coEvery { chatBotCloudStore.getChatBotUrl() } returns randomString
        chatBotDataRepository.getChatBotUrl()
        coVerify(exactly = 1) { chatBotCloudStore.getChatBotUrl() }
    }

    @Test(expected = Exception::class)
    fun `shouldn't find a chat bot url`() = runBlockingTest {
        coEvery { chatBotCloudStore.getChatBotUrl() } throws Exception()
        chatBotDataRepository.getChatBotUrl()
        coVerify(exactly = 1) { chatBotCloudStore.getChatBotUrl() }
    }
}
