@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.base.features.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetMenuOptionsUseCase
import biz.belcorp.salesforce.base.features.home.menu.bottommenu.BottomMenuViewModel
import biz.belcorp.salesforce.base.features.home.menu.bottommenu.BottomMenuViewState
import biz.belcorp.salesforce.base.features.home.menu.mappers.MenuOptionMapper
import biz.belcorp.salesforce.core.domain.entities.options.Option
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
class BottomMenuViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private var optionsUseCase = mockk<GetMenuOptionsUseCase>(relaxed = true)

    private val observer = mockk<Observer<BottomMenuViewState>>(relaxed = true)

    private lateinit var bottomMenuViewModel: BottomMenuViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        val mapper = MenuOptionMapper()
        bottomMenuViewModel = BottomMenuViewModel(optionsUseCase, mapper)
        bottomMenuViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `populate options menu`() = runBlockingTest {
        coEvery { optionsUseCase.getOptions() } returns getOptionsMock()
        bottomMenuViewModel.getOptionsMenu()
        verify(exactly = 1) {
            observer.onChanged(any<BottomMenuViewState.Populate>())
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    private fun getOptionsMock(): List<Option> {
        return listOf(
            Option(1, "MP1", 1, ""),
            Option(2, "MP2", 2, "")
        )
    }

}
