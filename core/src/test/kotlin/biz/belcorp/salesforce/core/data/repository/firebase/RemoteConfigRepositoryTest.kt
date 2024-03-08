@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.data.repository.firebase

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.FeaturesSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.domain.repository.firebase.RemoteConfigRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class RemoteConfigRepositoryTest {

    private val frcMock = mockk<FirebaseRemoteConfig>(relaxed = true)
    private val configPreferencesMock = mockk<ConfigSharedPreferences>(relaxed = true)
    private val featuresPreferencesMock = mockk<FeaturesSharedPreferences>(relaxed = true)
    private val userPreferencesMock = mockk<UserSharedPreferences>(relaxed = true)
    private val syncSharedPreferences = mockk<SyncSharedPreferences>(relaxed = true)

    private lateinit var repository: RemoteConfigRepository

    @Before
    fun setup() {
        repository = RemoteConfigDataRepository(
            frcMock,
            syncSharedPreferences,
            configPreferencesMock,
            featuresPreferencesMock,
            userPreferencesMock
        )
    }

    @Test
    fun `test fetch config`() = runBlockingTest {

        val slot = slot<OnCompleteListener<Boolean>>()

        every { frcMock.fetchAndActivate() } returns mockk {
            every { addOnCompleteListener(capture(slot)) } answers {
                slot.captured.onComplete(mockk {
                    every { isSuccessful } returns true
                })
                mockk()
            }
        }
        every { frcMock.getString(any()) } returns Constant.EMPTY_STRING

        repository.fetchConfig()
    }

}
