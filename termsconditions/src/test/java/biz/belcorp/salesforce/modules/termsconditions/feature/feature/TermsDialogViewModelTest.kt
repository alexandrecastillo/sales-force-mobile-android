@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.termsconditions.feature.feature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.usecases.terms.SyncTermsConditionsUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.termsconditions.features.dialog.TermsDialogViewModel
import biz.belcorp.salesforce.modules.termsconditions.features.dialog.TermsDialogViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TermsDialogViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val termUseCaseMock by lazy { mockk<TermConditionsUseCase>() }
    private val syncUseCaseMock by lazy { mockk<SyncTermsConditionsUseCase>() }
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var viewModel: TermsDialogViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = TermsDialogViewModel(syncUseCaseMock, termUseCaseMock)
    }

    @Test
    fun `saveTerms validation`() = runBlockingTest {
        coEvery { termUseCaseMock.saveApprovedTerms(any()) } returns true
        viewModel.saveTerms()
        viewModel.viewState.getOrAwaitValue()
        viewModel.viewState.value shouldBeInstanceOf (TermsDialogViewState.Success::class)
        val response = viewModel.viewState.value as TermsDialogViewState.Success
        response.success shouldEqual true
    }

    @Test
    fun `saveTerms List validation`() = runBlockingTest {
        coEvery { termUseCaseMock.saveApprovedTerms(any()) } returns true
        viewModel.saveTerms(listOf(ApproveTermsParams.TERM, ApproveTermsParams.PRIVACY))
        viewModel.viewState.getOrAwaitValue()
        viewModel.viewState.value shouldBeInstanceOf (TermsDialogViewState.Success::class)
        val response = viewModel.viewState.value as TermsDialogViewState.Success
        response.success shouldEqual true
    }

    @Test
    fun `getTermUrl validation failure`() = runBlockingTest {
        coEvery { termUseCaseMock.getTerm(any()) } returns Constant.EMPTY_STRING
        viewModel.getTermUrl(ApproveTermsParams.LINK_SE)
        viewModel.viewState.getOrAwaitValue()
        viewModel.viewState.value shouldBeInstanceOf (TermsDialogViewState.SuccessLink::class)
        val response = viewModel.viewState.value as TermsDialogViewState.SuccessLink
        response.url shouldEqual Constant.EMPTY_STRING
    }

    @Test
    fun `getUsername validation failure`() = runBlockingTest {
        coEvery { termUseCaseMock.getUserName() } returns Constant.EMPTY_STRING
        viewModel.getUsername()
        viewModel.viewState.getOrAwaitValue()
        viewModel.viewState.value shouldBeInstanceOf (TermsDialogViewState.SuccessName::class)
        val response = viewModel.viewState.value as TermsDialogViewState.SuccessName
        response.name shouldEqual Constant.EMPTY_STRING
    }

}
