@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.kpis.features.feature.features

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.KpiDetailParamsUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiDetailMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiDetailViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiDetailViewState
import biz.belcorp.salesforce.modules.kpis.utils.NewCycleIndicatorMock.kpiParams
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class KpiDetailViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val kpiDetailParamsUseCase by lazy { mockk<KpiDetailParamsUseCase>() }

    private lateinit var kpiDetailViewModel: KpiDetailViewModel

    private val observer = mockk<Observer<KpiDetailViewState>>(relaxed = true)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        kpiDetailViewModel = KpiDetailViewModel(kpiDetailParamsUseCase, KpiDetailMapper())
        kpiDetailViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `KpiDetail Sync validation`() = runBlockingTest {
        coEvery { kpiDetailParamsUseCase.getParams(KpiType.NEW_CYCLES) } returns kpiParams
        kpiDetailViewModel.getParams(KpiType.NEW_CYCLES)
        kpiDetailViewModel.viewState.getOrAwaitValue()
        verify(exactly = 1) { observer.onChanged(any<KpiDetailViewState.Success>()) }
    }

    @After
    fun after(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}
