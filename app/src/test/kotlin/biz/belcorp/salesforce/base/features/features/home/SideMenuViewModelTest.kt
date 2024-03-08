@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.base.features.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetMenuSubOptionsUseCase
import biz.belcorp.salesforce.base.core.domain.usecases.options.OptionsIdentifier
import biz.belcorp.salesforce.base.features.home.menu.mappers.MenuOptionMapper
import biz.belcorp.salesforce.base.features.home.menu.sidemenu.SideMenuViewModel
import biz.belcorp.salesforce.base.features.home.menu.sidemenu.SideMenuViewState
import biz.belcorp.salesforce.core.domain.entities.options.Option
import biz.belcorp.salesforce.core.domain.usecases.auth.LogoutUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*

@Ignore("No funciona con Sonar")
class SideMenuViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private var logoutUseCase = mockk<LogoutUseCase>(relaxed = true)
    private var optionsUseCase = mockk<GetMenuSubOptionsUseCase>(relaxed = true)
    private var linkUneteUseCase = mockk<TermConditionsUseCase>(relaxed = true)

    private val observer = mockk<Observer<SideMenuViewState>>(relaxed = true)

    private lateinit var sideMenuViewModel: SideMenuViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        val mapper = MenuOptionMapper()
        sideMenuViewModel = SideMenuViewModel(logoutUseCase, linkUneteUseCase, optionsUseCase, mapper)
        sideMenuViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `populate options menu`() = runBlockingTest {
        val parentCode = OptionsIdentifier.MORE
        coEvery { optionsUseCase.getOptions(parentCode) } returns getOptionsMock()
        sideMenuViewModel.getOptionsMenu(parentCode)
        verify(exactly = 1) {
            observer.onChanged(any<SideMenuViewState.Populate>())
        }
    }

    @Test
    fun `test logout`() = runBlockingTest {
        sideMenuViewModel.logout()
        verify(exactly = 1) {
            observer.onChanged(SideMenuViewState.LogoutSuccess)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    private fun getOptionsMock(): List<Option> {
        return listOf(
            Option(1, "ML1", 1, ""),
            Option(2, "ML2", 2, ""),
            Option(3, "ML3", 3, "")
        )
    }
}
