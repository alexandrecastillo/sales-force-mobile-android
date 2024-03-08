@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.kpis.features.feature.features.saleorders.multiprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_FOUR
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_THREE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.events.ConsumableEvent
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.GridUaInfo
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersConsolidated
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders.GridConsolidatedUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.SaleOrdersTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.mapper.GridConsolidatedMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SaleOrderGridType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid.ConsolidatedGridViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid.ConsolidatedGridViewState
import biz.belcorp.salesforce.modules.kpis.utils.DateUtils
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldHaveSize
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class SaleOrderGridViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: ConsolidatedGridViewModel
    private val campaignMock = mockk<Campania>()
    private val configurationMock = mockk<Configuration>()
    private val useCase = mockk<GridConsolidatedUseCase>(relaxed = true)
    private val textResolver = mockk<SaleOrdersTextResolver>(relaxed = true)
    private val mapper = GridConsolidatedMapper(textResolver)
    private val observer = mockk<Observer<ConsumableEvent>>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        every { campaignMock.esPrimerDiaFacturacion } returns false
        every { campaignMock.periodo } returns Campania.Periodo.VENTA
        every { campaignMock.codigo } returns CAMPAIGN_CODE
        every { campaignMock.nombreCorto } returns CAMPAIGN_SHORT_NAME

        every { configurationMock.isPdv } returns false
        every { configurationMock.currencySymbol } returns CURRENCY_SYMBOL

        every { textResolver.context.getString(R.string.text_goal) } returns GOAL
        every { textResolver.context.getString(R.string.text_zone_and_sections) } returns ZONE_TITLE
        every { textResolver.context.getString(R.string.text_real, *anyVararg()) } returns REAL
        every { textResolver.context.getString(R.string.text_goal, *anyVararg()) } returns GOAL
        every {
            textResolver.context.getString(
                R.string.text_fulfillment,
                *anyVararg()
            )
        } returns FULFILLMENT
        every {
            textResolver.context.getString(
                R.string.text_average,
                *anyVararg()
            )
        } returns AVERAGE
        every {
            textResolver.context.getString(
                R.string.text_low_value,
                *anyVararg()
            )
        } returns LOW_VALUE
        every {
            textResolver.context.getString(
                R.string.text_high_value,
                *anyVararg()
            )
        } returns HIGH_VALUE
        every {
            textResolver.context.getString(
                R.string.text_high_value_plus,
                *anyVararg()
            )
        } returns HIGH_VALUE_PLUS
        every {
            textResolver.context.getString(
                R.string.text_final_actives,
                *anyVararg()
            )
        } returns FINAL_ACTIVES
        every {
            textResolver.context.getString(
                R.string.text_final_actives_last_year,
                *anyVararg()
            )
        } returns FINAL_ACTIVES_LAST_YEAR
        every {
            textResolver.context.getString(
                R.string.text_actives_retention_percentage,
                *anyVararg()
            )
        } returns RETENTION
        every { textResolver.formatCurrencyAmount(*anyVararg()) } returns RANDOM_TEXT
        every { textResolver.formatPercentageStringLabel(*anyVararg()) } returns RANDOM_TEXT

        coEvery { useCase.getConsolidatedInfo(null) } returns SaleOrdersConsolidated(
            campaignMock,
            createUas(),
            configurationMock,
            createData(),
            Rol.GERENTE_ZONA
        )
        viewModel = ConsolidatedGridViewModel(useCase, mapper)
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `test getGridData change state`() = runBlockingTest {
        viewModel.getGridData(SaleOrderGridType.SALES)
        viewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any()) }
    }

    @Test
    fun `test getGridData for Sale Option`() = runBlockingTest {
        viewModel.getGridData(SaleOrderGridType.SALES)
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value?.value as ConsolidatedGridViewState.Success
        val grid = state.columns

        val columns = NUMBER_FOUR + NUMBER_ONE
        val rows = createUaKeys().size
        grid shouldHaveSize columns
        (grid.size * grid.first().values.size) shouldEqual (columns * rows)
        grid[NUMBER_ZERO].title.value shouldEqual ZONE_TITLE
        grid[NUMBER_ONE].title.value shouldEqual REAL
        grid[NUMBER_TWO].title.value shouldEqual GOAL
        grid[NUMBER_THREE].title.value shouldEqual FULFILLMENT
        grid[NUMBER_FOUR].title.value shouldEqual AVERAGE
    }

    @Test
    fun `test getGridData for Order Option`() = runBlockingTest {
        viewModel.getGridData(SaleOrderGridType.ORDERS)
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value?.value as ConsolidatedGridViewState.Success
        val grid = state.columns

        val columns = NUMBER_FOUR + NUMBER_THREE
        val rows = createUaKeys().size
        grid shouldHaveSize columns
        (grid.size * grid.first().values.size) shouldEqual (columns * rows)
        grid[NUMBER_ZERO].title.value shouldEqual ZONE_TITLE
        grid[NUMBER_ONE].title.value shouldEqual LOW_VALUE
        grid[NUMBER_TWO].title.value shouldEqual HIGH_VALUE
        grid[NUMBER_THREE].title.value shouldEqual HIGH_VALUE_PLUS
        grid[NUMBER_FOUR].title.value shouldEqual REAL
        grid[NUMBER_FOUR + NUMBER_ONE].title.value shouldEqual GOAL
        grid[NUMBER_FOUR + NUMBER_TWO].title.value shouldEqual FULFILLMENT
    }

    @Test
    fun `test getGridData for Activity Option`() = runBlockingTest {
        viewModel.getGridData(SaleOrderGridType.ACTIVITY)
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value?.value as ConsolidatedGridViewState.Success
        val grid = state.columns

        val columns = NUMBER_FOUR
        val rows = createUaKeys().size
        grid shouldHaveSize columns
        (grid.size * grid.first().values.size) shouldEqual (columns * rows)
        grid[NUMBER_ZERO].title.value shouldEqual ZONE_TITLE
        grid[NUMBER_ONE].title.value shouldEqual REAL
        grid[NUMBER_TWO].title.value shouldEqual GOAL
        grid[NUMBER_THREE].title.value shouldEqual FULFILLMENT
    }

    @Test
    fun `test getGridData for Actives Retention Option`() = runBlockingTest {
        viewModel.getGridData(SaleOrderGridType.ACTIVES_RETENTION)
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value?.value as ConsolidatedGridViewState.Success
        val grid = state.columns

        val columns = NUMBER_FOUR + NUMBER_TWO
        val rows = createUaKeys().size
        grid shouldHaveSize columns
        (grid.size * grid.first().values.size) shouldEqual (columns * rows)
        grid[NUMBER_ZERO].title.value shouldEqual ZONE_TITLE
        grid[NUMBER_ONE].title.value shouldEqual FINAL_ACTIVES
        grid[NUMBER_TWO].title.value shouldEqual FINAL_ACTIVES_LAST_YEAR
        grid[NUMBER_THREE].title.value shouldEqual RETENTION
        grid[NUMBER_FOUR].title.value shouldEqual GOAL
        grid[NUMBER_FOUR + NUMBER_ONE].title.value shouldEqual FULFILLMENT
    }

    private fun createData(): List<SaleOrdersIndicator> {
        return createUaKeys().map {
            SaleOrdersIndicator(
                campaign = CAMPAIGN_SHORT_NAME,
                region = REGION,
                zone = ZONE,
                section = it,
                profile = if (it.isEmpty()) "GZ" else "SE",
                fulfillmentOrderPercentage = Random.nextDouble(),
                salesRange = emptyList(),
                ordersRange = emptyList(),
                netSale = Random.nextDouble(),
                netSaleGoal = Random.nextDouble(),
                fulfillmentNetSalePercentage = Random.nextDouble(),
                catalogSale = Random.nextDouble(),
                fulfillmentCatalogSalesPercentage = Random.nextDouble(),
                catalogSaleGoal = Random.nextDouble(),
                orders = Random.nextInt(),
                ordersGoal = Random.nextInt(),
                highValueOrders = Random.nextInt(),
                lowValueOrders = Random.nextInt(),
                averageAmount = Random.nextDouble(),
                fulfillmentOrderAveragePercentage = Random.nextDouble(),
                activityPercentage = Random.nextDouble(),
                activityGoal = Random.nextDouble(),
                activityPegs = Random.nextInt(),
                fulfillmentActivityPercentage = Random.nextDouble(),
                activesInitials = Random.nextInt(),
                activesFinals = Random.nextInt(),
                activesRetentionPercentage = Random.nextDouble(),
                activesFinalsLastYear = Random.nextInt(),
                activesRetentionGoal = Random.nextDouble(),
                fulfillmentRetentionPercentage = Random.nextDouble(),
                averageAmountGoal = Random.nextDouble()

            )
        }
    }

    private fun createUaKeys() = listOf(EMPTY_STRING, SECTION_FIRST, SECTION_SECOND)

    private fun createUas() = listOf(
        GridUaInfo(createUaInfo(EMPTY_STRING, isParent = true), false),
        GridUaInfo(createUaInfo(SECTION_FIRST), false),
        GridUaInfo(createUaInfo(SECTION_SECOND), false)
    )

    private fun createUaInfo(section: String, isParent: Boolean = false) = UaInfo(
        code = Random.nextLong(),
        role = if (isParent) Rol.GERENTE_ZONA else Rol.SOCIA_EMPRESARIA,
        campaign = Campania.construirDummy(
            nombreCorto = CAMPAIGN_SHORT_NAME,
            codigo = CAMPAIGN_CODE
        ),
        isThirdPerson = !isParent,
        country = "PE",
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
        private const val CURRENCY_SYMBOL = "$"
        private const val CAMPAIGN_CODE = "202005"
        private const val CAMPAIGN_SHORT_NAME = "C05"
        private const val REGION = "09"
        private const val ZONE = "0920"
        private const val SECTION_FIRST = "A"
        private const val SECTION_SECOND = "B"
        private const val RANDOM_TEXT = "RANDOM_TEXT"
        private const val REAL = "REAL"
        private const val GOAL = "META"
        private const val FULFILLMENT = "CUMPLIMIENTO"
        private const val AVERAGE = "PROMEDIO POR\nPEDIDO"
        private const val ZONE_TITLE = "ZONA\nSECCIONES"
        private const val LOW_VALUE = "BAJOR VALOR"
        private const val HIGH_VALUE = "ALTO VALOR"
        private const val HIGH_VALUE_PLUS = "ALTO VALOR PLUS"
        private const val FINAL_ACTIVES = "ACTIVAS FINALES"
        private val FINAL_ACTIVES_LAST_YEAR = "ACTIVAS FINALES ${DateUtils.getLastYear()}"
        private const val RETENTION = "%RETENCIÓN DE ACRTIVAS"
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}
