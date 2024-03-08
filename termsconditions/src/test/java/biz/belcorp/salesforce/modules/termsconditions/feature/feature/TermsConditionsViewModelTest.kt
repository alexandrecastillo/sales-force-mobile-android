@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.termsconditions.feature.feature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.TermsConditionsMapper
import biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.TermsConditionsViewModel
import biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.TermsConditionsViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldHaveSize
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TermsConditionsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val termUseCaseMock by lazy { mockk<TermConditionsUseCase>() }
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var viewModel: TermsConditionsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        val mapper = TermsConditionsMapper()
        viewModel = TermsConditionsViewModel(termUseCaseMock, mapper)
    }

    @Test
    fun `getPoliticsTermsConditions empty validation`() = runBlockingTest {
        coEvery { termUseCaseMock.getTerms() } returns emptyList()
        viewModel.getPoliticsTermsConditions()
        viewModel.viewState.getOrAwaitValue()
        viewModel.viewState.value shouldBeInstanceOf (TermsConditionsViewState.Success::class)
        val response = viewModel.viewState.value as TermsConditionsViewState.Success
        response.data shouldHaveSize 0
    }

}
