@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.base.features.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetShortcutOptionsUseCase
import biz.belcorp.salesforce.base.features.home.shortcuts.ShortcutMapper
import biz.belcorp.salesforce.base.features.home.shortcuts.ShortcutsViewModel
import biz.belcorp.salesforce.base.features.home.shortcuts.ShortcutsViewState
import biz.belcorp.salesforce.core.domain.entities.options.Option
import biz.belcorp.salesforce.core.domain.repository.browser.DataReportRepository
import biz.belcorp.salesforce.core.domain.repository.browser.MyAcademyRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.browser.GetWebUrlUseCase
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
class ShortcutsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private var shortcutsUseCase = mockk<GetShortcutOptionsUseCase>(relaxed = true)

    private val dataReportRepositoryMock = mockk<DataReportRepository>(relaxed = true)
    private val myAcademyRepositoryMock = mockk<MyAcademyRepository>(relaxed = true)
    private val sessionRepositoryMock = mockk<SessionRepository>()

    private val observer = mockk<Observer<ShortcutsViewState>>(relaxed = true)

    private lateinit var viewModel: ShortcutsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        val mapper = ShortcutMapper()
        val getWebUrlUseCase = GetWebUrlUseCase(
            dataReportRepositoryMock,
            myAcademyRepositoryMock,
            sessionRepositoryMock
        )

        viewModel = ShortcutsViewModel(shortcutsUseCase, mapper, getWebUrlUseCase)
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `populate shortcuts`() = runBlockingTest {
        coEvery { shortcutsUseCase.getOptions() } returns getShortcutsMock()
        viewModel.getShortcuts()
        verify { observer.onChanged(any<ShortcutsViewState.Success>()) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    private fun getShortcutsMock(): List<Option> {
        return listOf(
            Option(10, "Reportes de Campaña", 10, ""),
            Option(11, "Únete", 11, ""),
            Option(12, "Ruta de desarrollo", 12, "")
        )
    }

}
