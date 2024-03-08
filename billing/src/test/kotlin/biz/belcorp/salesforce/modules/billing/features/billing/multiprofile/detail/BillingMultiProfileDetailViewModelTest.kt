@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.billing.features.billing.multiprofile.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.design.indicatorgoalbar.IndicatorGoalBar
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.billing.core.domain.entities.BillingMultiProfileDetailContainer
import biz.belcorp.salesforce.modules.billing.core.domain.entities.NewCycleBilling
import biz.belcorp.salesforce.modules.billing.core.domain.entities.PegsBilling
import biz.belcorp.salesforce.modules.billing.core.domain.entities.RejectedOrdersBilling
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.BillingMultiProfileDetailUseCase
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.rejectedorders.RejectedOrdersUseCase
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail.BillingMultiProfileDetailMapper
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail.BillingNewCycleMapper
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail.BillingPegsMapper
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail.BillingRejectedOrdersMapper
import biz.belcorp.salesforce.modules.billing.features.billing.model.detail.BillingNewCycleModel
import biz.belcorp.salesforce.modules.billing.features.billing.model.detail.BillingRejectedOrderModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.detail.BillingMultiProfileDetailViewModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.detail.BillingMultiProfileDetailViewState
import biz.belcorp.salesforce.modules.billing.features.billing.view.detail.RejectedOrdersViewState
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
import org.junit.*
import biz.belcorp.salesforce.components.R as ComponentsR

@Ignore("No funciona con Sonar")
class BillingMultiProfileDetailViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI Thread")

    private val billingUseCase = mockk<BillingMultiProfileDetailUseCase>()
    private val rejectedOrdersUseCase = mockk<RejectedOrdersUseCase>()

    private val billingPegMapper = mockk<BillingPegsMapper>()
    private val billingNewCycleMapper = mockk<BillingNewCycleMapper>()
    private val billingRejectedOrdersMapper = mockk<BillingRejectedOrdersMapper>()

    private val billingMapper =
        BillingMultiProfileDetailMapper(billingPegMapper, billingNewCycleMapper)

    private lateinit var viewModel: BillingMultiProfileDetailViewModel

    private val observer =
        mockk<Observer<BillingMultiProfileDetailViewState>>(relaxed = true)

    private val rejectedOrdersObserver =
        mockk<Observer<RejectedOrdersViewState>>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        every { billingPegMapper.map(false, any()) } returns createPegToModel()
        every { billingNewCycleMapper.map(false, any()) } returns Pair(Constant.EMPTY_STRING, createNewCycleToModel())
        every { billingRejectedOrdersMapper.map(any()) } returns createRejectedOrdersToModel()
        coEvery { billingUseCase.getBillingDetailAdvancement(any()) } returns createContainer()
        coEvery { rejectedOrdersUseCase.getRejectedOrders(any()) } returns createRejectedOrders()

        viewModel = BillingMultiProfileDetailViewModel(
            billingUseCase,
            rejectedOrdersUseCase,
            billingMapper,
            billingRejectedOrdersMapper
        )
        viewModel.viewState.observeForever(observer)
        viewModel.viewStateRejectedOrders.observeForever(rejectedOrdersObserver)
    }

    @Test
    fun `test for BillingMultiProfileDetailViewModel state`() = runBlockingTest {
        viewModel.getBillingDetailAdvancement(createUaKey())
        verify(exactly = 1) { observer.onChanged(any<BillingMultiProfileDetailViewState.Success>()) }
    }

    @Test
    fun `test for BillingMultiProfileDetailViewModel RejectedOrders state`() = runBlockingTest {
        viewModel.getRejectedOrders(createUaKey())
        verify(exactly = 1) { rejectedOrdersObserver.onChanged(any<RejectedOrdersViewState.Success>()) }
    }

    @Test
    fun `test for BillingMultiProfileDetailViewModel data`() = runBlockingTest {
        viewModel.getBillingDetailAdvancement(createUaKey())
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value as BillingMultiProfileDetailViewState.Success
        with(state) {
            model.pendingPegs.title shouldEqual PEG_TITLE
            model.pendingPegs.goalMessage shouldEqual PEG_MESSAGE
            model.pendingPegs.currentProgress shouldEqual PEG_PENDING
            model.pendingPegs.maxProgress shouldEqual PEG_TOTAL
            model.pendingPegs.currentTarget shouldEqual PEG_CURRENT_TARGET
            model.pendingPegs.enableAnimation shouldEqual false
            model.pendingPegs.goalDescription shouldEqual PEG_DESCRIPTION
            model.pendingPegs.goalMessageColor shouldEqual ComponentsR.color.magenta
            model.pendingPegs.type shouldEqual IndicatorGoalBar.GoalType.NORMAL

            model.pendingNewCycle shouldHaveSize Constant.NUMBER_TWO
        }

    }

    @Test
    fun `test for BillingMultiProfileDetailViewModel RejectedOrders data`() = runBlockingTest {
        viewModel.getRejectedOrders(createUaKey())
        viewModel.viewStateRejectedOrders.getOrAwaitValue()

        val state = viewModel.viewStateRejectedOrders.value as RejectedOrdersViewState.Success
        with(state) {
            model shouldHaveSize Constant.NUMBER_ZERO
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    private fun createUaKey() = LlaveUA(REGION, ZONE, Constant.EMPTY_STRING, -1)

    private fun createContainer(): BillingMultiProfileDetailContainer {
        return BillingMultiProfileDetailContainer(
            retainedPegs = createPendingPegs(),
            newCyclePendings = createPendingNewCycle(),
            isThirdPerson = false
        )
    }

    private fun createPegToModel(): IndicatorGoalBar.Model {
        val model = createPendingPegs()
        return IndicatorGoalBar.Model(
            title = PEG_TITLE,
            type = IndicatorGoalBar.GoalType.NORMAL,
            goalMessage = PEG_MESSAGE,
            goalMessageColor = ComponentsR.color.magenta,
            maxProgress = model.totalPegs,
            currentProgress = model.retainedPegs,
            goalDescription = PEG_DESCRIPTION,
            currentTarget = PEG_CURRENT_TARGET
        )
    }

    private fun createNewCycleToModel(): List<BillingNewCycleModel> {
        val models = createPendingNewCycle()

        return models.mapIndexed { index, _ ->
            BillingNewCycleModel(
                title = NEW_CYCLE_TITLE,
                description = NEW_CYCLE_DESCRIPTION,
                maxProgress = Constant.NUMBER_ONE_HUNDRED,
                progress = NEW_CYCLE_PROGRESS,
                progressLabel = NEW_CYCLE_LABEL,
                order = index
            )
        }
    }

    private fun createRejectedOrdersToModel(): List<BillingRejectedOrderModel> {
        val models = createRejectedOrders()
        return models.map {
            BillingRejectedOrderModel(title = it.title, quantity = it.quantity)
        }
    }

    private fun createPendingPegs(): PegsBilling {
        return PegsBilling(retainedPegs = PEG_PENDING, totalPegs = PEG_TOTAL)
    }

    private fun createPendingNewCycle(): List<NewCycleBilling> {
        return listOf(
            NewCycleBilling(
                highValueOrders6d6 = 10,
                highValueOrders5d5 = 11,
                highValueOrders4d4 = 7,
                highValueOrders3d3 = 8,
                lowValueOrders6d6 = 2,
                lowValueOrders5d5 = 3,
                lowValueOrders4d4 = 4,
                lowValueOrders3d3 = 5,
                lowValueOrders2d2 = 6,
                lowValueOrders1d1 = 7
            )
        )
    }

    private fun createRejectedOrders() = listOf<RejectedOrdersBilling>()

    companion object {
        private const val REGION = "06"
        private const val ZONE = "0620"

        private const val PEG_TITLE = "peg_title"
        private const val PEG_DESCRIPTION = "peg_description"
        private const val PEG_MESSAGE = "peg_message"
        private const val PEG_CURRENT_TARGET = "peg_current_target"
        private const val PEG_PENDING = 20
        private const val PEG_TOTAL = 23

        private const val NEW_CYCLE_TITLE = "new_cycle_title"
        private const val NEW_CYCLE_DESCRIPTION = "new_cycle_description"
        private const val NEW_CYCLE_LABEL = "new_cycle_label"
        private const val NEW_CYCLE_PROGRESS = 12
    }
}
