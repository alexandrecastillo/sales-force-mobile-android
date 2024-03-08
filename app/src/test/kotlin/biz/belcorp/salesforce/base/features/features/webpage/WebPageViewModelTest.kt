@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.base.features.features.webpage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.base.features.webpage.WebPageViewModel
import biz.belcorp.salesforce.base.features.webpage.WebPageViewState
import biz.belcorp.salesforce.core.domain.usecases.browser.GetWebUrlUseCase
import biz.belcorp.salesforce.core.domain.usecases.browser.WebTopic
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*


class WebPageViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var webPageViewModel: WebPageViewModel

    private val useCaseMock = mockk<GetWebUrlUseCase>()

    private val observer = mockk<Observer<WebPageViewState>>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        webPageViewModel = WebPageViewModel(useCaseMock)
        webPageViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `test get url LoadWebPage state`() = runBlockingTest {
        coEvery { useCaseMock.getUrl(any()) } returns ANY_URL
        webPageViewModel.getUrlByTopic(WebTopic.DATA_REPORT)
        webPageViewModel.viewState.getOrAwaitValue()
        verify(atLeast = 1) {
            observer.onChanged(any<WebPageViewState.LoadWebPage>())
        }
    }

    @Test
    fun `test get url Failed state`() = runBlockingTest {
        coEvery { useCaseMock.getUrl(any()) } throws Exception()
        webPageViewModel.getUrlByTopic(WebTopic.DATA_REPORT)
        webPageViewModel.viewState.getOrAwaitValue()
        verify(atLeast = 1) {
            observer.onChanged(any<WebPageViewState.Failed>())
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    companion object {

        private const val ANY_URL = "htto://noimporta.com"

    }

}
