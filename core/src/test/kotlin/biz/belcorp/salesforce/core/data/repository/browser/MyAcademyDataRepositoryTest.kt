@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.data.repository.browser

import biz.belcorp.salesforce.core.data.preferences.AuthSharedPreferences
import biz.belcorp.salesforce.core.data.repository.browser.cloud.MyAcademyCloudStore
import biz.belcorp.salesforce.core.domain.repository.browser.MyAcademyRepository
import biz.belcorp.salesforce.core.utils.parseToJson
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test


class MyAcademyDataRepositoryTest {

    private var cloudStoreMock = mockk<MyAcademyCloudStore>(relaxed = true)
    private var authPreferencesMock = mockk<AuthSharedPreferences>(relaxed = true)

    private lateinit var dataReportRepository: MyAcademyRepository

    @Before
    fun setup() {
        dataReportRepository = MyAcademyDataRepository(cloudStoreMock, authPreferencesMock)
    }

    @Test
    fun `test get url`() = runBlockingTest {

        mockkStatic(MAP_UTILS_CLASS)

        every { any<Map<*, *>>().parseToJson() } returns "{\"documento\":\"1076350875\"}"

        coEvery { cloudStoreMock.login(any(), any()) } returns mockk {
            every { campania } returns "202001"
            every { correo } returns "yeiesca@hotmail.com"
            every { segmentoConstancia } returns "Asesora de Belleza"
            every { esLider } returns "1"
            every { nivelLider } returns "0"
            every { campaniaInicioLider } returns ""
            every { seccionGestionLider } returns "141416A"
            every { accessToken } returns "TOKEN"
        }

        coEvery { cloudStoreMock.getUrl(any()) } returns mockk {
            every { data } returns mockk {
                every { urlMiAcademia } returns URL
            }
        }

        val url = dataReportRepository.getUrl("9900465065", "PE")

        url shouldBeEqualTo URL
    }

    companion object {

        private const val MAP_UTILS_CLASS = "biz.belcorp.salesforce.core.utils.MapUtilsKt"
        private const val URL = "http://qasucb.cyzone.com/moodleventas/auth/consultoralogin/ingresar.php?usuario=CO-9900465065&token=91be9e148fdba2a37d47fed52569befb"

    }

}
