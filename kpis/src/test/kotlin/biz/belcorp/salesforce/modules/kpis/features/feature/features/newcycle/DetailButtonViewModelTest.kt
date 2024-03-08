package biz.belcorp.salesforce.modules.kpis.features.feature.features.newcycle

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.detailbutton.GetDetailButtonUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.DetailButtonViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.DetailButtonViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.mapper.DetailButtonMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.model.DetailButtonInfoModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.model.DetailButtonType
import biz.belcorp.salesforce.modules.kpis.utils.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@Ignore
class DetailButtonViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val sessionUseCaseMock = mockk<ObtenerSesionUseCase>()
    private val getDetailButtonUseCaseMock = mockk<GetDetailButtonUseCase>()

    private lateinit var detailButtonViewModel: DetailButtonViewModel
    private val mapper by lazy { mockk<DetailButtonMapper>() }

    private val observer = mockk<Observer<DetailButtonViewState>>(relaxed = true)

    @Before
    fun setup() {
        detailButtonViewModel = DetailButtonViewModel(getDetailButtonUseCaseMock, sessionUseCaseMock, mapper)
        detailButtonViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `SE get detail button with ShowConsultantsButton`() {
        every { sessionUseCaseMock.obtener() } returns mockk {
            every { rol } returns Rol.SOCIA_EMPRESARIA
        }
        every { mapper.map(any(), any(), any(), any()) } returns DetailButtonInfoModel(DetailButtonType.CONSULTANT)
        coEvery { getDetailButtonUseCaseMock.isBillingForDetailButton() } returns Pair("", false)
        detailButtonViewModel.enableButton(KpiType.NEW_CYCLES)
        detailButtonViewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<DetailButtonViewState.Success>()) }
        val state = detailButtonViewModel.viewState.value as DetailButtonViewState.Success
        state.type shouldEqualTo DetailButtonType.CONSULTANT
    }

    @Test
    fun `SE get detail button with ShowBillingButton`() {
        every { sessionUseCaseMock.obtener() } returns mockk {
            every { rol } returns Rol.SOCIA_EMPRESARIA
        }
        every { mapper.map(any(), any(), any(), any()) } returns DetailButtonInfoModel(DetailButtonType.BILLING)
        coEvery { getDetailButtonUseCaseMock.isBillingForDetailButton() } returns Pair("", true)
        detailButtonViewModel.enableButton(KpiType.NEW_CYCLES)
        detailButtonViewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<DetailButtonViewState.Success>()) }
        val state = detailButtonViewModel.viewState.value as DetailButtonViewState.Success
        state.type shouldEqualTo DetailButtonType.BILLING
    }

    @Test
    fun `GZ get detail button with ShowBillingButton`() {
        every { sessionUseCaseMock.obtener() } returns mockk {
            every { rol } returns Rol.GERENTE_ZONA
        }
        every { mapper.map(any(), any(), any(), any()) } returns DetailButtonInfoModel(DetailButtonType.BILLING)
        coEvery { getDetailButtonUseCaseMock.isBillingForDetailButton() } returns Pair("", true)
        detailButtonViewModel.enableButton(KpiType.NEW_CYCLES)
        detailButtonViewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<DetailButtonViewState.Success>()) }
        val state = detailButtonViewModel.viewState.value as DetailButtonViewState.Success
        state.type shouldEqualTo DetailButtonType.BILLING
    }

    @Test
    fun `GZ get child detail button with ShowConsultantsButton`() {
        every { sessionUseCaseMock.obtener() } returns mockk {
            every { rol } returns Rol.GERENTE_ZONA
        }
        every { mapper.map(any(), any(), any(), any()) } returns DetailButtonInfoModel(DetailButtonType.CONSULTANT)
        coEvery { getDetailButtonUseCaseMock.isBillingForDetailButton() } returns Pair("", false)
        detailButtonViewModel.enableButton(KpiType.NEW_CYCLES)
        detailButtonViewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<DetailButtonViewState.Success>()) }
        val state = detailButtonViewModel.viewState.value as DetailButtonViewState.Success
        state.type shouldEqualTo DetailButtonType.CONSULTANT
    }

    @Test
    fun `GR get child detail button when is not billing, show nothing`() {
        every { sessionUseCaseMock.obtener() } returns mockk {
            every { rol } returns Rol.GERENTE_REGION
        }
        coEvery { getDetailButtonUseCaseMock.isBillingForDetailButton() } returns Pair("", false)
        detailButtonViewModel.enableButton(KpiType.NEW_CYCLES)
        detailButtonViewModel.viewState.getOrAwaitValue()
        verify(exactly = 0) { observer.onChanged(any<DetailButtonViewState.Success>()) }
    }

}
