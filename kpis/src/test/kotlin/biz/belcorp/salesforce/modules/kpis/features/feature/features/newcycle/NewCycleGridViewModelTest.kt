@file:Suppress("EXPERIMENTAL_API_USAGE")
package biz.belcorp.salesforce.modules.kpis.features.feature.features.newcycle

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.PeriodType
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.features.uainfo.UaInfoMapper
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.NewCycleMockDataHelper
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.newcycle.GetNewCycleIndicatorUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.NewCycleTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.mapper.NewCycleGridMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.consolidated.grid.NewCycleGridViewModel
import biz.belcorp.salesforce.modules.kpis.utils.CoroutinesTestRule
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.configuration
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.getSession
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.uaInfo
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.uaInfoModel
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.uaKey
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewCycleGridViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val useCaseMock = mockk<GetNewCycleIndicatorUseCase>()
    private val uaInfoUseCaseMock = mockk<UaInfoUseCase>()
    private val configurationUseCaseMock = mockk<ConfigurationUseCase>()
    private val getSessionUseCaseMock = mockk<ObtenerSesionUseCase>()
    private val newCycleMapper by lazy { mockk<NewCycleGridMapper>() }
    private val uaMapper by lazy { mockk<UaInfoMapper>() }
    private val textResolver by lazy { mockk<NewCycleTextResolver>() }

    private lateinit var newCycleGridViewModel: NewCycleGridViewModel

    private val observer = mockk<Observer<ConsumableEvent>>(relaxed = true)

    @Before
    fun setup() {
        newCycleGridViewModel =
            NewCycleGridViewModel(
                useCaseMock,
                uaInfoUseCaseMock,
                newCycleMapper,
                getSessionUseCaseMock
            )
        newCycleGridViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `get newcycle grid data list with success`() {
        every { uaMapper.map(uaInfo) } returns uaInfoModel
        every { newCycleMapper.map(any(), any(), any()) } returns arrayListOf()
        every { textResolver.getFirstColumnTitle(getSession().rol) } returns Constant.EMPTY_STRING
        every { getSessionUseCaseMock.obtener() } returns getSession()
        coEvery { useCaseMock.getNewCycleGridData(any()) } returns NewCycleMockDataHelper().fetchNewCycleGrid()
        coEvery { uaInfoUseCaseMock.getAssociatedUaListByUaKey(any(), any()) } returns listOf(uaInfo)
        coEvery { configurationUseCaseMock.getConfiguration(uaKey) } returns configuration
        newCycleGridViewModel.getGridData(PeriodType.SALE.toString(), uaKey)
        newCycleGridViewModel.viewState.getOrAwaitValue()
        coVerify { useCaseMock.getNewCycleGridData(any()) }
        verify { observer.onChanged(any()) }
    }

    @Test
    fun `throws exception when get newcycle grid data`() {
        every { uaMapper.map(uaInfo) } returns uaInfoModel
        every { newCycleMapper.map(any(), any(), any()) } returns arrayListOf()
        every { textResolver.getFirstColumnTitle(getSession().rol) } returns Constant.EMPTY_STRING
        every { getSessionUseCaseMock.obtener() } returns getSession()
        coEvery { useCaseMock.getNewCycleGridData(any()) } throws Exception()
        coEvery { uaInfoUseCaseMock.getAssociatedUaListByUaKey(any(), any()) } returns listOf(uaInfo)
        coEvery { configurationUseCaseMock.getConfiguration(uaKey) } returns configuration
        newCycleGridViewModel.getGridData(PeriodType.SALE.toString(), uaKey)
        newCycleGridViewModel.viewState.getOrAwaitValue()
        coVerify { useCaseMock.getNewCycleGridData(any()) }
        verify { observer.onChanged(any()) }
    }
}
