package biz.belcorp.salesforce.modules.kpis.features.feature.features.capitalization

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.mapper.CapitalizationMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.retention.CapitalizationMockDataHelper
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.CapitalizationKpiUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.mapper.CapitalizationKpiMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.sales.CapitalizationKpiDetailSeOnSalesViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.sales.CapitalizationKpiDetailSeOnSalesViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.capitalization.CapitalizationKpiOnBillingViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.capitalization.CapitalizationKpiOnBillingViewState
import biz.belcorp.salesforce.modules.kpis.utils.CapitalizationMock
import biz.belcorp.salesforce.modules.kpis.utils.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class RetentionCapiKpiViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val configurationUseCase = mockk<ConfigurationUseCase>()
    private val capitalizationKpiUseCase = mockk<CapitalizationKpiUseCase>()
    private val sessionUseCase = mockk<ObtenerSesionUseCase>()

    private val repositoryMapper = CapitalizationMapper()
    private val presentationMapper = mockk<CapitalizationKpiMapper>(relaxed = true)

    private lateinit var onSalesViewModel: CapitalizationKpiDetailSeOnSalesViewModel
    private lateinit var onBillingViewModel: CapitalizationKpiOnBillingViewModel

    private val onSalesObserver =
        mockk<Observer<CapitalizationKpiDetailSeOnSalesViewState>>(relaxed = true)

    private val onBillingObserver =
        mockk<Observer<CapitalizationKpiOnBillingViewState>>(relaxed = true)

    private val campaignMock =
        Campania("201918", "C-18", Date(), Date(), Date(), 0, true, Campania.Periodo.VENTA)

    @Before
    fun setup() {
        onSalesViewModel =
            CapitalizationKpiDetailSeOnSalesViewModel(
                capitalizationKpiUseCase,
                presentationMapper
            )

        onBillingViewModel = CapitalizationKpiOnBillingViewModel(
            capitalizationKpiUseCase,
            presentationMapper,
            configurationUseCase
        )

        onSalesViewModel.viewState.observeForever(onSalesObserver)
        onBillingViewModel.viewState.observeForever(onBillingObserver)
    }

    @Test
    fun `get sales-period kpi data success on captalization-kpi data`() {
        val mockupData = CapitalizationMockDataHelper.getCapitalizationEntities(
            CapitalizationMock.uaKeyMock,
            CapitalizationMock.campaignMock.codigo,
            repositoryMapper
        )

        coEvery {
            capitalizationKpiUseCase.getKpiData(any())
        } returns mockupData.first()

        onSalesViewModel.getCapitalizationData(ua = CapitalizationMock.uaKeyMock)
        onSalesViewModel.viewState.getOrAwaitValue()

        verify { onSalesObserver.onChanged(any<CapitalizationKpiDetailSeOnSalesViewState.Success>()) }
    }

    @Test
    fun `get billing-period kpi data success on captalization-kpi data`() {
        val mockupData = CapitalizationMockDataHelper.getCapitalizationEntities(
            CapitalizationMock.uaKeyMock,
            CapitalizationMock.campaignMock.codigo,
            repositoryMapper
        )

        coEvery {
            capitalizationKpiUseCase.getKpiData(any())
        } returns mockupData.first()

        onBillingViewModel.getCapitalizationData(ua = CapitalizationMock.uaKeyMock)
        onBillingViewModel.viewState.getOrAwaitValue()

        verify { onBillingObserver.onChanged(any<CapitalizationKpiOnBillingViewState.Success>()) }
    }

}
