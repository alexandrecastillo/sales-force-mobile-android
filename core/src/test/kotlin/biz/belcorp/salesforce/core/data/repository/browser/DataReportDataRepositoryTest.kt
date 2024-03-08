@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.data.repository.browser

import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.domain.repository.browser.DataReportRepository
import biz.belcorp.salesforce.core.utils.parseToJson
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test


class DataReportDataRepositoryTest {

    private var userPreferencesMock = mockk<UserSharedPreferences>(relaxed = true)

    private lateinit var dataReportRepository: DataReportRepository

    @Before
    fun setup() {
        dataReportRepository = DataReportDataRepository(userPreferencesMock)
    }

    @Test
    fun `test get url for SE`() = runBlockingTest {

        mockkStatic(MAP_UTILS_CLASS)

        every { any<Map<*, *>>().parseToJson() } returns REQUEST_SE_JSON

        with(userPreferencesMock) {
            every { username } returns "9900465065"
            every { consultoraId } returns null
            every { codPais } returns "CO"
            every { codRol } returns "SE"
        }

        val url = dataReportRepository.getUrlForSE()

        url shouldBeEqualTo SE_URL
    }

    @Test
    fun `test get url for GZ`() = runBlockingTest {

        mockkStatic(MAP_UTILS_CLASS)

        every { any<Map<*, *>>().parseToJson() } returns REQUEST_GZ_JSON

        with(userPreferencesMock) {
            every { username } returns "ntrejos"
            every { codPais } returns "CO"
            every { codRol } returns "GZ"
        }

        val url = dataReportRepository.getUrlForGZ()

        url shouldBeEqualTo GZ_URL
    }

    companion object {

        private const val MAP_UTILS_CLASS = "biz.belcorp.salesforce.core.utils.MapUtilsKt"

        private const val REQUEST_SE_JSON = "{\"FuenteOrigen\":\"App\",\"CodigoConsultora\":\"9900465065\",\"ConsultoraID\":null,\"CodigoISO\":\"CO\",\"CodigoRol\":\"SE\",\"Pagina\":\"DataReportSE\"}"
        private const val REQUEST_GZ_JSON = "{\"FuenteOrigen\":\"App\",\"CodigoConsultora\":\"ntrejos\",\"CodigoISO\":\"CO\",\"CodigoRol\":\"GZ\",\"Pagina\":\"DataReportGZ\"}"

        private const val SE_URL = "https://ffvvmqa.somosbelcorp.com/portal/IngresoSistema/IngresoExterno?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJGdWVudGVPcmlnZW4iOiJBcHAiLCJDb2RpZ29Db25zdWx0b3JhIjoiOTkwMDQ2NTA2NSIsIkNvbnN1bHRvcmFJRCI6bnVsbCwiQ29kaWdvSVNPIjoiQ08iLCJDb2RpZ29Sb2wiOiJTRSIsIlBhZ2luYSI6IkRhdGFSZXBvcnRTRSJ9.1pOR2Nc6__8LWlcjTXxbZtX-iOzpR40v-FWxRtXdLe0"
        private const val GZ_URL = "https://ffvvmqa.somosbelcorp.com/portal/IngresoSistema/IngresoExterno?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJGdWVudGVPcmlnZW4iOiJBcHAiLCJDb2RpZ29Db25zdWx0b3JhIjoibnRyZWpvcyIsIkNvZGlnb0lTTyI6IkNPIiwiQ29kaWdvUm9sIjoiR1oiLCJQYWdpbmEiOiJEYXRhUmVwb3J0R1oifQ.h4h_C4cfmrAhDvSpt2fdt1a_Gvlr1SeS_05LeEfSjTg"

    }

}
