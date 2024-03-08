@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.kpis.features.feature.features.saleorders.multiprofile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiData
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrderMultiProfileContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders.SaleOrdersUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.*
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile.SaleOrdersHeaderViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile.SaleOrdersHeaderViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile.mapper.SaleOrderMapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldHaveSize
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class SaleOrderHeaderMultiProfileViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: SaleOrdersHeaderViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val saleOrdersUseCase = mockk<SaleOrdersUseCase>(relaxed = true)
    private val saleOrderMapper = mockk<SaleOrderMapper>(relaxed = true)
    private val observer = mockk<Observer<SaleOrdersHeaderViewState>>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        coEvery { saleOrdersUseCase.getSaleOrderMultiProfile(any()) } returns createContainer()
        every { saleOrderMapper.map(any(), UserProperties.session?.campaign?.periodo) } returns createModel()
        viewModel = SaleOrdersHeaderViewModel(saleOrdersUseCase, saleOrderMapper)
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `test getSaleHeaderOrders change observer`() = runBlockingTest {
        viewModel.getSaleHeaderOrders()
        viewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<SaleOrdersHeaderViewState.Success>()) }
    }

    @Test
    fun `test getSaleHeaderOrders GZ`() = runBlockingTest {
        viewModel.getSaleHeaderOrders()
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value as SaleOrdersHeaderViewState.Success
        with(state) {
            data.goals shouldHaveSize SIZE
            data.goals shouldHaveSize SIZE
            data.titleAchievements shouldContain "achievement"
            data.titleGoals shouldContain "goal"
        }
    }

    private fun createModel() = SaleOrdersHeaderModel(
        titleGoals = "goal",
        achievements = createContentModels(),
        titleAchievements = "achievements",
        goals = createContentModels()
    )

    private fun createContentModels(): List<ContentModel> {
        val models = arrayListOf<ContentModel>()
        for (i in 0 until SIZE) {
            val model = ContentModel(type = ContentType.SIMPLE_CARD, items = createBaseModels())
            models.add(model)
        }
        return models
    }

    private fun createBaseModels() = listOf(
        Single(EMPTY_STRING, EMPTY_STRING),
        Separator(),
        Single(EMPTY_STRING, EMPTY_STRING),
        Separator(),
        Single(EMPTY_STRING, EMPTY_STRING)
    )

    private fun createContainer() = SaleOrderMultiProfileContainer(
        saleOrdersIndicators = getKpiData(),
        isThirdPerson = false,
        isBilling = false,
        currencySymbol = "$"
    )

    private fun getKpiData(): KpiData<SaleOrdersIndicator> {
        val list = createIndicators()
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, "202005", "202004")
    }

    private fun createIndicators(): List<SaleOrdersIndicator> {
        val indicators = arrayListOf<SaleOrdersIndicator>()
        for (i in 0 until SIZE) {
            val indicator = SaleOrdersIndicator(
                campaign = getCampaign(i),
                fulfillmentRetentionPercentage = createRandomDouble(),
                region = EMPTY_STRING,
                zone = EMPTY_STRING,
                section = EMPTY_STRING,
                activesRetentionGoal = createRandomDouble(),
                activesFinalsLastYear = createRandomInt(),
                activesRetentionPercentage = createRandomDouble(),
                activesFinals = createRandomInt(),
                activesInitials = createRandomInt(),
                fulfillmentActivityPercentage = createRandomDouble(),
                activityPegs = createRandomInt(),
                activityGoal = createRandomDouble(),
                activityPercentage = createRandomDouble(),
                fulfillmentOrderPercentage = createRandomDouble(),
                fulfillmentOrderAveragePercentage = createRandomDouble(),
                averageAmount = createRandomDouble(),
                lowValueOrders = createRandomInt(),
                highValueOrders = createRandomInt(),
                ordersGoal = createRandomInt(),
                orders = createRandomInt(),
                catalogSaleGoal = createRandomDouble(),
                fulfillmentCatalogSalesPercentage = createRandomDouble(),
                catalogSale = createRandomDouble(),
                fulfillmentNetSalePercentage = createRandomDouble(),
                netSaleGoal = createRandomDouble(),
                netSale = createRandomDouble(),
                ordersRange = emptyList(),
                salesRange = emptyList(),
                averageAmountGoal = createRandomDouble()
            )
            indicators.add(indicator)
        }
        return indicators
    }

    private fun getCampaign(i: Int): String {
        return if (i == 0) "202005" else "202004"
    }

    private fun createRandomInt() = Random.nextInt(0, 10)

    private fun createRandomDouble() = Random.nextDouble(0.0, 5.0)

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    companion object {
        private const val SIZE = 2
    }
}
