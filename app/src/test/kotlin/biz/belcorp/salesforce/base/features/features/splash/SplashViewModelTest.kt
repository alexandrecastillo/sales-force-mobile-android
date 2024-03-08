package biz.belcorp.salesforce.base.features.features.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.base.features.splash.SplashViewModel
import biz.belcorp.salesforce.base.features.splash.SplashViewState
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val useCaseMock by lazy { mockk<ObtenerSesionUseCase>() }
    private val termUseCaseMock by lazy { mockk<TermConditionsUseCase>() }

    private lateinit var splashViewModel: SplashViewModel

    private val observer = mockk<Observer<SplashViewState>>(relaxed = true)

    @Before
    fun setup() {
        splashViewModel = SplashViewModel(useCaseMock,termUseCaseMock)
        splashViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `Authenticated state validation`() {
        every { useCaseMock.isAvailable() } returns true
        splashViewModel.checkSession()
        verify (atLeast = 1){
            observer.onChanged(SplashViewState.Authenticated)
        }
    }

    @Test
    fun `Unauthenticated state validation`() {
        every { useCaseMock.isAvailable() } returns false
        splashViewModel.checkSession()
        verify (atLeast = 1){
            observer.onChanged(SplashViewState.Unauthenticated)
        }
    }

}
