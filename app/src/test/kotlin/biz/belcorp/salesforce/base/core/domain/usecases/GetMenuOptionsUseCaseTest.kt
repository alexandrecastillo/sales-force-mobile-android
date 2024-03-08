package biz.belcorp.salesforce.base.core.domain.usecases

import biz.belcorp.salesforce.base.core.domain.repository.options.OptionsRepository
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetMenuOptionsUseCase
import biz.belcorp.salesforce.core.domain.entities.options.Option
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldHaveSize
import org.junit.Before
import org.junit.Test


class GetMenuOptionsUseCaseTest {

    private val repository = mockk<OptionsRepository>()
    private lateinit var useCase: GetMenuOptionsUseCase

    @Before
    fun setup() {
        useCase = GetMenuOptionsUseCase(repository)
    }

    @Test
    fun `get sub options`() = runBlockingTest {
        coEvery { repository.getMenuOptions() } returns getOptionsMock()
        val subOptions = useCase.getOptions()
        subOptions shouldHaveSize 3
    }

    private fun getOptionsMock(): List<Option> {
        return listOf(
            Option(1, "MP1", 1, ""),
            Option(2, "MP2", 2, ""),
            Option(3, "MP3", 3, "")
        )
    }

}
