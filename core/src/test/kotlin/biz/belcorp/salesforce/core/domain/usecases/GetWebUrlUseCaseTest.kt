@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.core.domain.usecases

import biz.belcorp.salesforce.core.domain.repository.browser.DataReportRepository
import biz.belcorp.salesforce.core.domain.repository.browser.MyAcademyRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.browser.GetWebUrlUseCase
import biz.belcorp.salesforce.core.domain.usecases.browser.WebTopic
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class GetWebUrlUseCaseTest {

    private val dataReportRepositoryMock = mockk<DataReportRepository>(relaxed = true)
    private val myAcademyRepositoryMock = mockk<MyAcademyRepository>(relaxed = true)
    private val sessionRepositoryMock = mockk<SessionRepository>()

    private lateinit var useCase: GetWebUrlUseCase

    @Before
    fun setup() {
        useCase = GetWebUrlUseCase(
            dataReportRepositoryMock,
            myAcademyRepositoryMock,
            sessionRepositoryMock
        )
    }

    @Test
    fun `test get url for my academy`() = runBlockingTest {

        every { sessionRepositoryMock.getSession() } returns mockk {
            every { person } returns mockk {
                every { document } returns "76543210"
            }
            every { countryIso } returns "CO"
        }
        coEvery { myAcademyRepositoryMock.getUrl(any(), any()) } returns ANY_URL

        useCase.getUrl(WebTopic.MY_ACADEMY)

        coVerify(exactly = 1) { myAcademyRepositoryMock.getUrl("76543210", "CO") }
    }

    @Test
    fun `test get url for data report SE`() = runBlockingTest {
        every { sessionRepositoryMock.getSession() } returns mockk {
            every { rol } returns Rol.SOCIA_EMPRESARIA
        }
        coEvery { dataReportRepositoryMock.getUrlForSE() } returns ANY_URL
        useCase.getUrl(WebTopic.DATA_REPORT)
        coVerify(exactly = 1) { dataReportRepositoryMock.getUrlForSE() }
    }

    @Test
    fun `test get url for data report GZ`() = runBlockingTest {
        every { sessionRepositoryMock.getSession() } returns mockk {
            every { rol } returns Rol.GERENTE_ZONA
        }
        coEvery { dataReportRepositoryMock.getUrlForGZ() } returns ANY_URL
        useCase.getUrl(WebTopic.DATA_REPORT)
        coVerify(exactly = 1) { dataReportRepositoryMock.getUrlForGZ() }
    }

    @Test
    fun `test get url for ucb`() = runBlockingTest {
        val url = useCase.getUrl(WebTopic.UCB)
        url shouldBeEqualTo UCB_HOST
    }

    companion object {

        private const val ANY_URL = "htto://noimporta.com"
        private const val UCB_HOST = "https://miscursosucb.belcorp.biz/"

    }

}
