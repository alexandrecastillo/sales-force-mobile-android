@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.base.features.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.features.home.HomeMapper
import biz.belcorp.salesforce.base.features.home.HomeModel
import biz.belcorp.salesforce.base.features.home.HomeViewModel
import biz.belcorp.salesforce.base.features.home.HomeViewState
import biz.belcorp.salesforce.base.features.utils.AppTextResolver
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.usecases.auth.LogoutUseCase
import biz.belcorp.salesforce.core.domain.usecases.device.UpdateTokenUseCase
import biz.belcorp.salesforce.core.domain.usecases.firebase.FetchRemoteConfigUseCase
import biz.belcorp.salesforce.core.domain.usecases.firebase.FirebaseCrashlyticsUseCase
import biz.belcorp.salesforce.core.domain.usecases.firebase.ManageTopicsUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.uainfo.UaInfoMapper
import biz.belcorp.salesforce.core.features.utils.CoreTextResolver
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.messaging.core.domain.usecases.GetNotificationUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val subscribeTopicsUseCase = mockk<ManageTopicsUseCase>(relaxed = true)
    private val fetchConfigUseCase = mockk<FetchRemoteConfigUseCase>(relaxed = true)
    private val crashlyticsUseCase = mockk<FirebaseCrashlyticsUseCase>(relaxed = true)
    private val updateTokenUseCase = mockk<UpdateTokenUseCase>(relaxed = true)
    private val uaUseCase = mockk<UaInfoUseCase>(relaxed = true)
    private val logoutUseCase = mockk<LogoutUseCase>(relaxed = true)

    private val person = mockk<Person>()
    private val campaign = mockk<Campania>()
    private val sessionUseCase = mockk<ObtenerSesionUseCase>()
    private val termUseCase = mockk<TermConditionsUseCase>()
    private val getNotificationUseCase = mockk<GetNotificationUseCase>()
    private val textResolver = mockk<AppTextResolver>(relaxed = true)
    private val coreTextResolver = mockk<CoreTextResolver>(relaxed = true)
    private lateinit var homeMapper: HomeMapper
    private lateinit var uaInfoMapper: UaInfoMapper

    private val observer = mockk<Observer<HomeViewState>>(relaxed = true)

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        every { campaign.nombreCorto } returns EMPTY_STRING
        every { sessionUseCase.obtener() } returns createSession(Rol.NINGUNO)
        every { textResolver.formatToolbarSaleText(*anyVararg()) } returns DEFAULT_TOOLBAR_SALE
        every { textResolver.formatToolbarBillingText(*anyVararg()) } returns DEFAULT_TOOLBAR_BILLING

        homeMapper = HomeMapper(textResolver)
        uaInfoMapper = UaInfoMapper(coreTextResolver)

        viewModel =
            HomeViewModel(
                sessionUseCase,
                getNotificationUseCase,
                uaUseCase,
                termUseCase,
                homeMapper,
                uaInfoMapper
            )
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `test Toolbar for Billing period`() = runBlockingTest {
        every { sessionUseCase.obtener() } returns createSession(Rol.SOCIA_EMPRESARIA)
        every { campaign.periodo } returns Campania.Periodo.FACTURACION
        every { campaign.esPrimerDiaFacturacion } returns false
        every { campaign.inicioFacturacion } returns Date()
        viewModel.getPeriodInformation()
        viewModel.viewState.getOrAwaitValue()
        verify(exactly = 1) { observer.onChanged(any<HomeViewState.Success>()) }
        val response = viewModel.viewState.value as HomeViewState.Success
        with(response.model) {
            periodDescription shouldNotEqual EMPTY_STRING
            isBilling shouldEqual true
            color shouldEqual R.color.magenta
        }
    }

    @Test
    fun `test Toolbar for Sale period`() {
        every { sessionUseCase.obtener() } returns createSession(Rol.DIRECTOR_VENTAS)
        every { campaign.periodo } returns Campania.Periodo.VENTA
        every { campaign.esPrimerDiaFacturacion } returns false
        every { campaign.inicioFacturacion } returns Date()
        viewModel.getPeriodInformation()
        viewModel.viewState.getOrAwaitValue()
        verify(exactly = 1) { observer.onChanged(any<HomeViewState.Success>()) }
        val response = viewModel.viewState.value as HomeViewState.Success
        with(response.model) {
            periodDescription shouldNotEqual EMPTY_STRING
            isBilling shouldEqual false
            color shouldEqual R.color.colorPrimaryText
        }
    }

    @Test
    fun `test for Banner Sale period`() {
        val model =
            createHomeModel(Rol.GERENTE_REGION, isBilling = false, isFirstDayBilling = false)
        val result = homeMapper.isBillingByRole(model)

        result shouldEqual false
    }

    @Test
    fun `test for Banner Billing period DV & GR (false)`() {
        val model =
            createHomeModel(Rol.GERENTE_REGION, isBilling = true, isFirstDayBilling = true)
        val result = homeMapper.isBillingByRole(model)

        result shouldEqual false
    }

    @Test
    fun `test for Banner Billing period DV & GR (true)`() {
        val model =
            createHomeModel(Rol.GERENTE_REGION, isBilling = true, isFirstDayBilling = false)
        val result = homeMapper.isBillingByRole(model)

        result shouldEqual true
    }

    @Test
    fun `test for Banner Billing period GZ & SE`() {
        val model =
            createHomeModel(role = Rol.SOCIA_EMPRESARIA, isBilling = true, isFirstDayBilling = true)
        val result = homeMapper.isBillingByRole(model)

        result shouldEqual true
    }

    private fun createHomeModel(role: Rol, isBilling: Boolean, isFirstDayBilling: Boolean) =
        HomeModel(
            periodDescription = EMPTY_STRING,
            color = R.color.magenta,
            role = role,
            isBilling = isBilling,
            campaignName = EMPTY_STRING,
            isFirstDayBilling = isFirstDayBilling
        )

    private fun createSession(role: Rol): Sesion {
        return Sesion(
            null,
            null,
            EMPTY_STRING,
            null,
            role.codigoRol,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            person,
            campaign
        )
    }

    companion object {
        private const val DEFAULT_TOOLBAR_BILLING = "Billiing"
        private const val DEFAULT_TOOLBAR_SALE = "Sale"
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

}
