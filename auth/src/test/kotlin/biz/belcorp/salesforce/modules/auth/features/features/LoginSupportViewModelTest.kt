@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.auth.features.features

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.login.request.RequestSupport
import biz.belcorp.salesforce.core.utils.AppBuild
import biz.belcorp.salesforce.core.domain.usecases.features.GetFeaturesUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import biz.belcorp.salesforce.core.sync.groups.LoginSyncGroupManager
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.auth.core.domain.usecases.LoginType
import biz.belcorp.salesforce.modules.auth.core.domain.usecases.LoginUseCase
import biz.belcorp.salesforce.modules.auth.features.base.LoginViewState
import biz.belcorp.salesforce.modules.auth.features.support.LoginSupportViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*


class LoginSupportViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private var loginUseCaseMock = mockk<LoginUseCase>(relaxed = true)
    private var featuresUseCaseMock = mockk<GetFeaturesUseCase>(relaxed = true)
    private var termUseCaseMock = mockk<TermConditionsUseCase>(relaxed = true)
    private var syncManagerMock = mockk<LoginSyncGroupManager>(relaxed = true)
    private var appBuildMock = mockk<AppBuild>(relaxed = true)

    private lateinit var supportViewModel: LoginSupportViewModel

    private val observer = mockk<Observer<LoginViewState>>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        supportViewModel =
            LoginSupportViewModel(
                loginUseCaseMock,
                featuresUseCaseMock,
                termUseCaseMock,
                syncManagerMock,
                appBuildMock
            )
        supportViewModel.viewState.observeForever(observer)
    }

    @Test
    @Ignore("No funciona con Sonar")
    fun `test login support`() {

        coEvery { loginUseCaseMock.login(any()) } returns LoginType.ONLINE

        supportViewModel.support(requestSupport)
        supportViewModel.viewState.getOrAwaitValue()

        verify(atLeast = 1) { observer.onChanged(LoginViewState.LoginSuccess) }
    }

    @Test
    @Ignore("No funciona con Sonar")
    fun `test start sync online`() = runBlockingTest {

        coEvery { loginUseCaseMock.login(any()) } returns LoginType.ONLINE

        supportViewModel.support(requestSupport)
        supportViewModel.viewState.getOrAwaitValue()

        verify(atLeast = 1) { observer.onChanged(LoginViewState.LoginSuccess) }

        supportViewModel.startSync()
        supportViewModel.viewState.getOrAwaitValue()

        coVerify(exactly = 1) { syncManagerMock.start() }
        verify(atLeast = 1) { observer.onChanged(LoginViewState.SyncSuccess) }
    }

    @Test
    @Ignore("No funciona con Sonar")
    fun `test start sync offline`() {

        coEvery { loginUseCaseMock.login(any()) } returns LoginType.OFFLINE

        supportViewModel.support(requestSupport)
        supportViewModel.viewState.getOrAwaitValue()

        verify(atLeast = 1) { observer.onChanged(LoginViewState.LoginSuccess) }

        supportViewModel.startSync()
        supportViewModel.viewState.getOrAwaitValue()

        coVerify(exactly = 0) { syncManagerMock.start() }
        verify(atLeast = 1) { observer.onChanged(LoginViewState.SyncSuccess) }
    }

    @Test
    @Ignore("No funciona con Sonar")
    fun `test login fail`() {

        coEvery { loginUseCaseMock.login(any()) } throws Exception()

        supportViewModel.support(requestSupport)
        supportViewModel.viewState.getOrAwaitValue()

        verify(exactly = 1) { observer.onChanged(any<LoginViewState.LoginError>()) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    private val requestSupport
        get() = mockk<RequestSupport> {
            every { country } returns mockk {
                every { iso } returns "PE"
            }
            every { username } returns "username"
            every { password } returns "password"
            every { region } returns "01"
            every { zone } returns "1212"
            every { section } returns "A"
        }

}
