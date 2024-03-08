package biz.belcorp.salesforce.core.data.repository.firebase

import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.domain.repository.firebase.CloudMessagingRepository
import biz.belcorp.salesforce.core.utils.AppBuildConfig
import com.google.firebase.messaging.FirebaseMessaging
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.Before
import org.junit.Test


class CloudMessagingRepositoryTest {

    private lateinit var repository: CloudMessagingRepository

    private val fcmMock = mockk<FirebaseMessaging>(relaxed = true)
    private val configPreferences = mockk<ConfigSharedPreferences>(relaxed = true)

    @Before
    fun before() {
        mockkObject(AppBuildConfig)
        every { AppBuildConfig.getBuildType() } returns "DEVELOP"
        repository = CloudMessagingDataRepository(fcmMock, configPreferences)
    }

    @Test
    fun `test subscribe to topics`() {

        val topics = listOf(
            "PUSH_RC"
        )

        repository.subscribeTopics(topics)

        verify(exactly = 1) { fcmMock.subscribeToTopic("PUSH_RC") }

    }

    @Test
    fun `test unsubscribe to topics`() {

        val topics = listOf(
            "PUSH_RC"
        )

        repository.unsubscribeTopics(topics)

        verify(exactly = 1) { fcmMock.unsubscribeFromTopic("PUSH_RC") }

    }

    @Test
    fun `test subscribe to topics with env`() {

        val topics = listOf(
            "ZONIFICATION_PE",
            "ZONIFICATION_PE_01",
            "ZONIFICATION_PE_01_0110",
            "LEADER_PE_01_0110"
        )

        repository.subscribeTopicsWithEnv(topics)

        verify(exactly = 1) { fcmMock.subscribeToTopic("DEVELOP_ZONIFICATION_PE") }
        verify(exactly = 1) { fcmMock.subscribeToTopic("DEVELOP_ZONIFICATION_PE_01") }
        verify(exactly = 1) { fcmMock.subscribeToTopic("DEVELOP_ZONIFICATION_PE_01_0110") }
        verify(exactly = 1) { fcmMock.subscribeToTopic("DEVELOP_LEADER_PE_01_0110") }

    }

    @Test
    fun `test unsubscribe to topics with env`() {

        val topics = listOf(
            "ZONIFICATION_PE",
            "ZONIFICATION_PE_01",
            "ZONIFICATION_PE_01_0110",
            "LEADER_PE_01_0110"
        )

        repository.unsubscribeTopicsWithEnv(topics)

        verify(exactly = 1) { fcmMock.unsubscribeFromTopic("DEVELOP_ZONIFICATION_PE") }
        verify(exactly = 1) { fcmMock.unsubscribeFromTopic("DEVELOP_ZONIFICATION_PE_01") }
        verify(exactly = 1) { fcmMock.unsubscribeFromTopic("DEVELOP_ZONIFICATION_PE_01_0110") }
        verify(exactly = 1) { fcmMock.unsubscribeFromTopic("DEVELOP_LEADER_PE_01_0110") }

    }

}
