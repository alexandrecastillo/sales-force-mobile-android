@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.base.features.features.cookies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import biz.belcorp.salesforce.base.features.home.cookies.TermsCookiesViewModel
import biz.belcorp.salesforce.base.features.home.cookies.TermsCookiesViewState
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.terms.ApproveTermsParams
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TermsCookiesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val termUseCaseMock by lazy { mockk<TermConditionsUseCase>() }
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var cookiesViewModel: TermsCookiesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        cookiesViewModel = TermsCookiesViewModel(termUseCaseMock)
    }

    @Test
    fun `saveTerm validation`() = runBlockingTest {
        coEvery { termUseCaseMock.saveApprovedTerms(any()) } returns true
        cookiesViewModel.saveTerms()
        cookiesViewModel.viewState.getOrAwaitValue()
        cookiesViewModel.viewState.value shouldBeInstanceOf (TermsCookiesViewState.SuccessApproved::class)
        val response = cookiesViewModel.viewState.value as TermsCookiesViewState.SuccessApproved
        response.success shouldEqual true
    }

    @Test
    fun `getTermUrl validation`() = runBlockingTest {
        coEvery { termUseCaseMock.getTerm(any()) } returns ""
        cookiesViewModel.getTermUrl(ApproveTermsParams.COOKIES)
        cookiesViewModel.viewState.getOrAwaitValue()
        cookiesViewModel.viewState.value shouldBeInstanceOf (TermsCookiesViewState.SuccessLink::class)
        val response = cookiesViewModel.viewState.value as TermsCookiesViewState.SuccessLink
        response.url shouldBeEqualTo  Constant.EMPTY_STRING
    }
}
