@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.base.core.domain.usecases

import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.businesspartner.BusinessPartnerRepository
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.directory.ManagerDirectoryRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.repository.ua.UaInfoRepository
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldHaveSize
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class UaUseCaseTest {

    private val sessionRepository = mockk<SessionRepository>(relaxed = true)
    private val uaRepository = mockk<UaInfoRepository>(relaxed = true)
    private val managerRepository = mockk<ManagerDirectoryRepository>(relaxed = true)
    private val partnersRepository = mockk<BusinessPartnerRepository>(relaxed = true)
    private val campaignsRepository = mockk<CampaniasRepository>(relaxed = true)
    private lateinit var uaUseCase: UaInfoUseCase

    @Before
    fun setup() {
        uaUseCase =
            UaInfoUseCase(
                sessionRepository,
                uaRepository,
                managerRepository,
                partnersRepository,
                campaignsRepository
            )
    }

    @Test
    fun `get uas by rol`() = runBlockingTest {
        coEvery { managerRepository.getManagers() } returns createPeople()
        coEvery { uaRepository.getAssociatedUaListByUaKey(any()) } returns createUas()
        val totalUas = uaUseCase.getAssociatedUaListByUaKey(createUaKey())
        totalUas shouldHaveSize 4
    }

    @Test(expected = Exception::class)
    fun `get uas by rol with error`() = runBlockingTest {
        coEvery { uaRepository.getAssociatedUaListByUaKey(any()) } throws Exception("error")
        uaUseCase.getAssociatedUaListByUaKey(createUaKey())
        coVerify { uaRepository.getAssociatedUaListByUaKey(createUaKey()) }
    }

    private fun createPeople() = emptyList<Person>()

    private fun createUas(): List<UaInfo> {
        return listOf(
            UaInfo(
                code = Random.nextLong(),
                country = "PE",
                role = Rol.GERENTE_ZONA,
                person = null,
                campaign = null,
                uaKey = createUaKey(),
                isCovered = true,
                isThirdPerson = false
            ),
            UaInfo(
                code = Random.nextLong(),
                country = "PE",
                role = Rol.GERENTE_ZONA,
                person = null,
                campaign = null,
                uaKey = createUaKey(),
                isCovered = true,
                isThirdPerson = false
            ),
            UaInfo(
                code = Random.nextLong(),
                country = "PE",
                role = Rol.GERENTE_ZONA,
                person = null,
                campaign = null,
                uaKey = createUaKey(),
                isCovered = true,
                isThirdPerson = false
            ),
            UaInfo(
                code = Random.nextLong(),
                country = "PE",
                role = Rol.GERENTE_ZONA,
                person = null,
                campaign = null,
                uaKey = createUaKey(),
                isCovered = true,
                isThirdPerson = false
            )
        )
    }

    private fun createUaKey() = LlaveUA("09", "", "", -1)
}
