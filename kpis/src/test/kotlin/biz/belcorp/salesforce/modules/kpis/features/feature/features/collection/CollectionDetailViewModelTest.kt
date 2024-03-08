package biz.belcorp.salesforce.modules.kpis.features.feature.features.collection

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.CollectionTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.CollectionDetailViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.GainDetailViewState
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.uaKey
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
class CollectionDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val textResolver = mockk<CollectionTextResolver>()

    private val observer = mockk<Observer<GainDetailViewState>>(relaxed = true)

    private lateinit var viewModel: CollectionDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = CollectionDetailViewModel(textResolver)
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `should response GainDetailViewState$Success`() = runBlockingTest {
        every { textResolver.obtainGridTitleDetail(any()) } returns EMPTY_STRING
        viewModel.getGainInformation(uaKey)
        viewModel.viewState.getOrAwaitValue()
        verify(exactly = 1) { observer.onChanged(any<GainDetailViewState.Success>()) }
    }

    @Test
    fun `should response GainDetailViewState$Failed`() = runBlockingTest {
        every { textResolver.obtainGridTitleDetail(any()) } throws Exception()
        viewModel.getGainInformation(uaKey)
        viewModel.viewState.getOrAwaitValue()
        verify(exactly = 1) { observer.onChanged(any<GainDetailViewState.Failed>()) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

}
