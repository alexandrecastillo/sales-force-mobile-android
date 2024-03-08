package biz.belcorp.salesforce.modules.kpis.features.feature.features.capitalization

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.PostulantKpi
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.PostulantKpiUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.postulants.PostulantKpiMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.postulants.PostulantKpiViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.postulants.PostulantKpiViewState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostulantViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: PostulantKpiViewModel
    private val useCase = mockk<PostulantKpiUseCase>(relaxed = true)
    private val mapper = mockk<PostulantKpiMapper>(relaxed = true)

    private val observer =
        mockk<Observer<PostulantKpiViewState>>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        viewModel = PostulantKpiViewModel(useCase, mapper)
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `get kpi data success on postulants-kpi data`() {
        val uaKeyMock = LlaveUA("13", "1311", "A", 0L)

        coEvery { useCase.getPostulantsKpi(any()) } returns mockkPostulantData()

        viewModel.getData(uaKeyMock)
        viewModel.viewState.getOrAwaitValue()

        verify { observer.onChanged(any<PostulantKpiViewState.Success>()) }
    }

    private fun mockkPostulantData() = listOf(
        PostulantKpi(
            currentCampaign = "202004",
            preApproved = 10,
            approved = 103,
            inEvaluation = 40,
            anticipatedEntries = 80
        )
    )

}
