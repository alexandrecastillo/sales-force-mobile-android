@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.kpis.features.feature.features.saleorders.multiprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.uainfo.UaInfoMapper
import biz.belcorp.salesforce.core.features.uainfo.UaInfoModel
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import biz.belcorp.salesforce.modules.kpis.utils.SaleOrdersMock.uaKeyMock
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class SaleOrderUaDetailMultiProfileViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: UaDetailViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val sessionUseCase = mockk<ObtenerSesionUseCase>(relaxed = true)
    private val uaUseCase = mockk<UaInfoUseCase>(relaxed = true)
    private val uaInfoMapper = mockk<UaInfoMapper>(relaxed = true)
    private val observer = mockk<Observer<UaDetailViewState>>(relaxed = true)
    private val uaInfoModel = mockk<UaInfoModel>()

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        every { sessionUseCase.obtener() } returns mockk {
            every { llaveUA } returns mockk {
                every { region } returns "09"
                every { zona } returns "0920"
                every { seccion } returns EMPTY_STRING
            }
            every { rol } returns Rol.GERENTE_REGION
        }
        coEvery { uaUseCase.getAssociatedUaListByUaKey(any()) } returns createUas()
        every { uaInfoMapper.mapToSelector(any()) } returns createSelectors()

        viewModel = UaDetailViewModel(uaUseCase, sessionUseCase, uaInfoMapper)

        viewModel.uaViewState.observeForever(observer)
    }

    @Test
    fun `test to get Uas For Grid`() = runBlockingTest {
        viewModel.getUaInformation()
        viewModel.uaViewState.getOrAwaitValue()
        verify { observer.onChanged(any<UaDetailViewState.Success>()) }
    }

    @Test
    fun `test sendUa`() {
        coEvery { uaInfoModel.uaKey } returns uaKeyMock
        viewModel.updateChildDetail(uaInfoModel)
        viewModel.uaViewState.getOrAwaitValue()
        viewModel.uaViewState.value shouldBeInstanceOf (UaDetailViewState.UpdateItem::class)
        val response = viewModel.uaViewState.value as UaDetailViewState.UpdateItem
        response.uaKey shouldNotBe EMPTY_STRING
    }

    private fun createPeople() = listOf(
        BusinessPartner(
            id = Random.nextLong(),
            code = "1",
            secondSurname = EMPTY_STRING,
            secondName = EMPTY_STRING,
            firstName = EMPTY_STRING,
            document = EMPTY_STRING,
            firstSurname = EMPTY_STRING,
            birthDate = EMPTY_STRING,
            anniversaryDate = EMPTY_STRING,
            leaderClassification = EMPTY_STRING,
            level = "BRONZE",
            levelCode = "0",
            status = EMPTY_STRING
        ),
        BusinessPartner(
            id = Random.nextLong(),
            code = "2",
            secondSurname = EMPTY_STRING,
            secondName = EMPTY_STRING,
            firstName = EMPTY_STRING,
            document = EMPTY_STRING,
            firstSurname = EMPTY_STRING,
            birthDate = EMPTY_STRING,
            anniversaryDate = EMPTY_STRING,
            leaderClassification = EMPTY_STRING,
            level = "BRONZE",
            levelCode = "0",
            status = EMPTY_STRING
        )
    )

    private fun createSelectors() = listOf(
        SelectorModel(
            key = EMPTY_STRING,
            isSelected = true,
            label = EMPTY_STRING
        ).apply { model = EMPTY_STRING }
    )

    private fun createUas(): List<UaInfo> {
        return listOf(
            UaInfo(
                code = Random.nextLong(),
                country = "PE",
                role = Rol.SOCIA_EMPRESARIA,
                person = createPeople().first(),
                campaign = Campania.construirDummy(),
                uaKey = createUaKey("A"),
                isCovered = true,
                isThirdPerson = false
            ),
            UaInfo(
                code = Random.nextLong(),
                country = "PE",
                role = Rol.SOCIA_EMPRESARIA,
                person = createPeople().last(),
                campaign = Campania.construirDummy(),
                uaKey = createUaKey("B"),
                isCovered = true,
                isThirdPerson = false
            )
        )
    }

    private fun createUaKey(section: String) =
        LlaveUA("09", "0906", section, -1)

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}
