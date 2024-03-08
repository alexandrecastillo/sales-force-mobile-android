package biz.belcorp.salesforce.modules.kpis.features.feature.features.collection

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection.GetGainUseCase
import biz.belcorp.salesforce.modules.kpis.features.feature.features.collection.CollectionDataHelper.collectionConsolidatedMock
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.CollectionTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid.GainConsolidatedGridViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid.GainConsolidatedGridViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid.GainConsolidatedMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid.creator.GainGridCreator
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.days
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.uaKey
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.*

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class GainConsolidatedGridViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val textResolver = mockk<CollectionTextResolver>(relaxed = true)
    private val getGainUseCase = mockk<GetGainUseCase>()

    private val gainGridCreator = GainGridCreator()
    private val consolidatedMapper = GainConsolidatedMapper(textResolver, gainGridCreator)

    private lateinit var viewModel: GainConsolidatedGridViewModel

    private val observer = mockk<Observer<ConsumableEvent>>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = GainConsolidatedGridViewModel(
            getGainUseCase,
            consolidatedMapper
        )
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `should response Success`() = runBlockingTest {
        coEvery { getGainUseCase.getUaData(uaKey,any()) } returns collectionConsolidatedMock
        viewModel.getGridData(uaKey,"21")
        val consumableEvent = viewModel.viewState.getOrAwaitValue()
        consumableEvent.value shouldBeInstanceOf(GainConsolidatedGridViewState.Success::class.java)
    }

    @Test
    fun `should response Failed`() = runBlockingTest {
        coEvery { getGainUseCase.getUaData(uaKey,any()) } throws Exception()
        viewModel.getGridData(uaKey,days)
        val consumableEvent = viewModel.viewState.getOrAwaitValue()
        consumableEvent.value shouldBe GainConsolidatedGridViewState.NonDataAvailable
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

}
