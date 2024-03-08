package biz.belcorp.salesforce.modules.kpis.features.feature.features.collection

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection.GetGainUseCase
import biz.belcorp.salesforce.modules.kpis.features.feature.features.collection.CollectionDataHelper.collectionContainerMock
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.CollectionTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.header.multiprofile.GainHeaderMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.header.multiprofile.GainHeaderViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.header.multiprofile.GainHeaderViewState
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.uaKey
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class GainHeaderViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val getGainUseCase = mockk<GetGainUseCase>()
    private val textResolver = mockk<CollectionTextResolver>(relaxed = true)
    private val gainHeaderMapper = GainHeaderMapper(textResolver)

    private val observer = mockk<Observer<GainHeaderViewState>>(relaxed = true)

    private lateinit var viewModel: GainHeaderViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = GainHeaderViewModel(getGainUseCase, gainHeaderMapper)
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `should response GainHeaderViewState$Success`() = runBlockingTest {
        coEvery { getGainUseCase.getGainInformation(uaKey) } returns collectionContainerMock
        viewModel.getGainHeaderInformation(uaKey)
        viewModel.viewState.getOrAwaitValue()
        coVerify(exactly = 1) { observer.onChanged(any<GainHeaderViewState.Success>()) }
    }

    @Test
    fun `should response GainHeaderViewState$Failed`() = runBlockingTest {
        coEvery { getGainUseCase.getGainInformation(uaKey) } throws Exception()
        viewModel.getGainHeaderInformation(uaKey)
        viewModel.viewState.getOrAwaitValue()
        coVerify(exactly = 1) { observer.onChanged(any<GainHeaderViewState.Failed>()) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

}
