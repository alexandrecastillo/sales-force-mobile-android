@file:Suppress("EXPERIMENTAL_API_USAGE")
package biz.belcorp.salesforce.modules.kpis.features.feature.features.newcycle

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.NewCycleMockDataHelper
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.newcycle.GetNewCycleIndicatorUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.mapper.NewCycleIndicatorMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.NewCycleIndicatorViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.header.NewCycleHeaderViewState
import biz.belcorp.salesforce.modules.kpis.utils.CoroutinesTestRule
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.campaignMock
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.uaKey
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewCycleIndicatorViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val useCaseMock = mockk<GetNewCycleIndicatorUseCase>()
    private val campaignUseCaseMock = mockk<ObtenerCampaniasUseCase>()
    private val mapper by lazy { mockk<NewCycleIndicatorMapper>() }

    private lateinit var newCycleIndicatorViewModel: NewCycleIndicatorViewModel

    private val observer = mockk<Observer<NewCycleHeaderViewState>>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        newCycleIndicatorViewModel =
            NewCycleIndicatorViewModel(
                useCaseMock,
                mapper
            )
        newCycleIndicatorViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `get newcycle indicator list with success`() {
        val newCycleIndicators = NewCycleMockDataHelper().fetchNewCycle()
        coEvery { useCaseMock.getNewCycleIndicator(uaKey) } returns newCycleIndicators
        coEvery { campaignUseCaseMock.obtenerCampaniaActual() } returns campaignMock
        coEvery { campaignUseCaseMock.getPreviousCampaignSuspend(uaKey) } returns campaignMock
        newCycleIndicatorViewModel.fetchNewCycleIndicator(uaKey)
        newCycleIndicatorViewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<NewCycleHeaderViewState.NewCycleIndicatorListSuccess>()) }
    }

    @Test
    fun `throws exception when get newcycle indicator`() {
        coEvery { useCaseMock.getNewCycleIndicator(uaKey) } throws Exception()
        coEvery { campaignUseCaseMock.obtenerCampaniaActual() } returns campaignMock
        coEvery { campaignUseCaseMock.getPreviousCampaignSuspend(uaKey) } returns campaignMock
        newCycleIndicatorViewModel.fetchNewCycleIndicator(uaKey)
        newCycleIndicatorViewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<NewCycleHeaderViewState.NewCycleIndicatorError>()) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}
