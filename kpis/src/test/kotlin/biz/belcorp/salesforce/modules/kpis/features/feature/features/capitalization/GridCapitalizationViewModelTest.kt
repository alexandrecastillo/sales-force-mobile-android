@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.kpis.features.feature.features.capitalization

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_FOUR
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_THREE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.GridUaInfo
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.GridConsolidatedUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.CapitalizationTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.grid.GridCapitalizationKpiViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.grid.GridCapitalizationMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.grid.GridCapitalizationViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.model.CapitalizationConsolidated
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.filtergrid.types.CapitalizationGridType
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldHaveSize
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class GridCapitalizationViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: GridCapitalizationKpiViewModel

    private val useCaseMock = mockk<GridConsolidatedUseCase>(relaxed = true)
    private val textResolverMock = mockk<CapitalizationTextResolver>(relaxed = true)
    private val mapperMock = GridCapitalizationMapper(textResolverMock)

    private val observer = mockk<Observer<GridCapitalizationViewState>>(relaxed = true)

    private val campaign = mockk<Campania> {
        every { codigo } returns "202008"
        every { nombreCorto } returns "C-08"
        every { shortNameOnly } returns "C08"
    }

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        every { textResolverMock.context.getString(R.string.text_zone_and_sections) } returns ZONE_TITLE
        every { textResolverMock.context.getString(R.string.pegs, *anyVararg()) } returns PEGS
        every { textResolverMock.context.getString(R.string.pegs_column_title, *anyVararg()) } returns PEGS
        every {
            textResolverMock.context.getString(
                R.string.title_retention_pegs,
                *anyVararg()
            )
        } returns PEGS_RETENTION
        every {
            textResolverMock.context.getString(
                R.string.text_fulfillment,
                *anyVararg()
            )
        } returns FULFILLMENT
        every { textResolverMock.context.getString(R.string.incomes, *anyVararg()) } returns ENTRIES
        every {
            textResolverMock.context.getString(
                R.string.reentries,
                *anyVararg()
            )
        } returns REENTRIES
        every {
            textResolverMock.context.getString(
                R.string.expenses,
                *anyVararg()
            )
        } returns EXPENSES
        every {
            textResolverMock.context.getString(
                R.string.capitalization,
                *anyVararg()
            )
        } returns CAPITALIZATION
        every {
            textResolverMock.context.getString(
                R.string.capitalization_projected,
                *anyVararg()
            )
        } returns CAPITALIZATION
        every { textResolverMock.context.getString(R.string.text_goal, *anyVararg()) } returns GOAL

        coEvery { useCaseMock.getCapitalizationInfo(any()) } returns CapitalizationConsolidated(
            uas = createUAs(),
            values = dataMock(),
            role = Rol.GERENTE_ZONA,
            campaign = campaign,
            currentCampaign = campaign,
            isBilling = true
        )

        viewModel = GridCapitalizationKpiViewModel(useCaseMock, mapperMock)
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `test grid-data on change state - capitalization option`() = runBlockingTest {
        viewModel.getGridData(CapitalizationGridType.CAPITALIZATION, getUaKey())
        viewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<GridCapitalizationViewState.Success>()) }
    }

    @Test
    fun `test grid-data on change state - pegs option`() = runBlockingTest {
        viewModel.getGridData(CapitalizationGridType.PEGS, getUaKey())
        viewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<GridCapitalizationViewState.Success>()) }
    }

    @Test
    fun `text grid-data for capitalization filter`() = runBlockingTest {
        viewModel.getGridData(CapitalizationGridType.CAPITALIZATION, getUaKey())
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value as GridCapitalizationViewState.Success
        val grid = state.data

        val rows = createUaKeys().size
        val columns = NUMBER_FOUR + NUMBER_TWO

        grid shouldHaveSize columns
        (grid.size * grid.first().values.size) shouldEqual (columns * rows)
        grid[NUMBER_ZERO].title.value shouldEqual ZONE_TITLE
        grid[NUMBER_ONE].title.value shouldContain ENTRIES
        grid[NUMBER_TWO].title.value shouldContain REENTRIES
        grid[NUMBER_THREE].title.value shouldContain EXPENSES
        grid[NUMBER_FOUR].title.value shouldContain CAPITALIZATION
        grid[NUMBER_FOUR + NUMBER_ONE].title.value shouldContain GOAL
    }

    @Test
    fun `text grid-data for pegs filter`() = runBlockingTest {
        viewModel.getGridData(CapitalizationGridType.PEGS, getUaKey())
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value as GridCapitalizationViewState.Success
        val grid = state.data

        val rows = createUaKeys().size
        val columns = NUMBER_THREE

        grid shouldHaveSize columns
        (grid.size * grid.first().values.size) shouldEqual (columns * rows)
        grid[NUMBER_ZERO].title.value shouldEqual ZONE_TITLE
        grid[NUMBER_ONE].title.value shouldContain PEGS
        grid[NUMBER_TWO].title.value shouldContain PEGS_RETENTION
    }

    private fun getUaKey() = LlaveUA(REGION, ZONE, EMPTY_STRING, 0L)

    private fun createUAs() = listOf(
        GridUaInfo(createUaInfo(EMPTY_STRING, isParent = true), false),
        GridUaInfo(createUaInfo(FIRST_SECTION), false),
        GridUaInfo(createUaInfo(SECOND_SECTION), false)
    )

    private fun createUaKeys() = listOf(
        EMPTY_STRING,
        FIRST_SECTION,
        SECOND_SECTION
    )

    private fun dataMock(): List<CapitalizationIndicator> {
        return createUaKeys().map {
            CapitalizationIndicator(
                campaign = CAMPAIGN_SHORT_NAME,
                region = REGION,
                zone = ZONE,
                section = it,
                capitalizationReal = Random.nextInt(),
                capitalizationGoal = Random.nextInt(),
                capitalizationFulfillment = Random.nextDouble(),
                capitalizationProjected = Random.nextInt(),
                capitalizationEntries = Random.nextInt(),
                capitalizationEntriesGoal = Random.nextInt(),
                capitalizationReentries = Random.nextInt(),
                capitalizationExpenses = Random.nextInt(),
                pegsReal = Random.nextInt(),
                pegRetentionGoal = Random.nextInt(),
                pegRetentionReal = Random.nextInt(),
                pegRetentionPercentage = Random.nextDouble(),
                pegRetentionRemaining = Random.nextInt(),
                potentialTotal = Random.nextInt(),
                potentialEntries = Random.nextInt(),
                potentialReentries = Random.nextInt()
            )
        }
    }

    private fun createUaInfo(section: String, isParent: Boolean = false) = UaInfo(
        code = Random.nextLong(),
        role = if (isParent) Rol.GERENTE_ZONA else Rol.SOCIA_EMPRESARIA,
        campaign = Campania.construirDummy(
            nombreCorto = CAMPAIGN_SHORT_NAME,
            codigo = CAMPAIGN_CODE
        ),
        isThirdPerson = !isParent,
        country = "CO",
        isCovered = true,
        person = createPerson(),
        uaKey = LlaveUA(REGION, ZONE, section)
    )

    private fun createPerson() = BusinessPartner(
        id = Random.nextLong(),
        status = EMPTY_STRING,
        levelCode = EMPTY_STRING,
        level = EMPTY_STRING,
        leaderClassification = EMPTY_STRING,
        anniversaryDate = EMPTY_STRING,
        birthDate = EMPTY_STRING,
        firstSurname = EMPTY_STRING,
        document = EMPTY_STRING,
        firstName = EMPTY_STRING,
        secondName = EMPTY_STRING,
        secondSurname = EMPTY_STRING,
        code = EMPTY_STRING
    )


    companion object {
        private const val CAMPAIGN_CODE = "202005"
        private const val CAMPAIGN_SHORT_NAME = "C05"
        private const val REGION = "13"
        private const val ZONE = "1311"
        private const val FIRST_SECTION = "B"
        private const val SECOND_SECTION = "H"

        private const val ZONE_TITLE = "ZONA\nSECCIONES"

        private const val PEGS = "PEGS"
        private const val PEGS_RETENTION = "RETENCIÓN\nPEGS"

        private const val ENTRIES = "INRESOS"
        private const val REENTRIES = "REINGRESOS"
        private const val EXPENSES = "EGRESOS"
        private const val CAPITALIZATION = "CAPITALIZACIÓN"
        private const val GOAL = "META"
        private const val FULFILLMENT = "CUMPLIMIENTO"

    }

}
