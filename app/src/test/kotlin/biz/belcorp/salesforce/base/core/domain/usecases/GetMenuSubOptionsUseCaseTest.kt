package biz.belcorp.salesforce.base.core.domain.usecases

import biz.belcorp.salesforce.base.core.domain.repository.options.OptionsRepository
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetMenuSubOptionsUseCase
import biz.belcorp.salesforce.core.domain.entities.options.Option
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldHaveSize
import org.junit.Before
import org.junit.Test


class GetMenuSubOptionsUseCaseTest {

    private val repository = mockk<OptionsRepository>()
    private lateinit var useCase: GetMenuSubOptionsUseCase

    @Before
    fun setup() {
        useCase = GetMenuSubOptionsUseCase(repository)
    }

    @Test
    fun `get sub options`() = runBlockingTest {
        val parentCode = 1
        coEvery { repository.getMenuSubOptions(parentCode) } returns getOptionsMock()
        val subOptions = useCase.getOptions(parentCode)
        subOptions shouldHaveSize 3
    }

    private fun getOptionsMock(): List<Option> {
        return listOf(
            Option(1, "ML1", 1, ""),
            Option(2, "ML2", 2, ""),
            Option(3, "ML3", 3, "")
        )
    }

}
