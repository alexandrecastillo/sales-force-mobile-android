@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.billing.features.billing.multiprofile.header

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.billing.Billing
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.GetBillingAdvancementUseCase
import biz.belcorp.salesforce.modules.billing.features.billing.mapper.BillingMultiProfileHeaderMapper
import biz.belcorp.salesforce.modules.billing.features.billing.model.AdvancementParams
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingHeaderTextResolver
import biz.belcorp.salesforce.modules.billing.features.billing.view.header.multiprofile.BillingMultiProfileHeaderViewModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.header.multiprofile.BillingMultiProfileHeaderViewState
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
import org.junit.*

class BillingMultiProfileHeaderViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI Thread")

    private val billingAdvancementUseCase = mockk<GetBillingAdvancementUseCase>(relaxed = true)
    private val billingTextResolver = mockk<BillingHeaderTextResolver>(relaxed = true)

    private val observer = mockk<Observer<BillingMultiProfileHeaderViewState>>(relaxed = true)

    private val mapper = BillingMultiProfileHeaderMapper(billingTextResolver)
    private lateinit var viewModelMultiProfileHeader: BillingMultiProfileHeaderViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        coEvery {
            billingAdvancementUseCase.getBillingAdvancement(any())
        } returns createBilling()

        every { billingTextResolver.getTitleSaleBilling() } returns TITLE_NET_SALE_BILLING
        every { billingTextResolver.getTitleOrdersBilling() } returns TITLE_ORDERS_BILLING
        every { billingTextResolver.getAmountWithCurrency(*anyVararg()) } returns CURRENCY_WITH_AMOUNT
        every {
            billingTextResolver.getSaleBrightDescription(*anyVararg(), isThirdPerson = false)
        } returns DESCRIPTION_NET_SALE_ACHIEVED
        every {
            billingTextResolver.getSaleSuccessBrightDescription(*anyVararg(), isThirdPerson = true)
        } returns DESCRIPTION_NET_SALE_REACHED
        every {
            billingTextResolver.getOrdersDescription(
                *anyVararg(), quantity = 2, isThirdPerson = false
            )
        } returns DESCRIPTION_NET_SALE_NOT_REACHED
        every {
            billingTextResolver.getOrdersSuccessDescription(
                *anyVararg(), quantity = 2, isThirdPerson = true
            )
        } returns "RCH_NET_SALE_2"

        viewModelMultiProfileHeader =
            BillingMultiProfileHeaderViewModel(billingAdvancementUseCase, mapper)
        viewModelMultiProfileHeader.viewState.observeForever(observer)
    }

    @Test
    fun `test for BillingMultiProfileHeaderViewModel state`() = runBlockingTest {
        viewModelMultiProfileHeader.getBillingInformation(createUaKey())
        viewModelMultiProfileHeader.viewState.getOrAwaitValue()
        verify(exactly = 1) { observer.onChanged(any<BillingMultiProfileHeaderViewState.Success>()) }
    }

    @Test
    fun `test for BillingMultiProfileHeaderViewModel data`() = runBlockingTest {

        viewModelMultiProfileHeader.getBillingInformation(createUaKey())
        viewModelMultiProfileHeader.viewState.getOrAwaitValue()
        val state =
            viewModelMultiProfileHeader.viewState.value as BillingMultiProfileHeaderViewState.Success
        with(state) {
            model.titleSale shouldEqual TITLE_NET_SALE_BILLING
            model.titleOrders shouldEqual TITLE_ORDERS_BILLING
        }

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    private fun createBilling(): Billing {
        return Billing(
            fulfillmentCatalogSalesPercentage = 0.20,
            fulfillmentNetSalesPercentage = 0.20,
            ordersGoal = 50,
            catalogSalesGoal = 50.0,
            currentCatalogSales = 20.0,
            currentNetSales = 80.0,
            currentOrders = 30,
            fulfillmentOrdersPercentage = 0.20,
            netSalesGoal = 90.0
        ).apply {
            isThirdPerson = false
            currencySymbol = "$"
            isBright = false
        }
    }

    private fun createUaKey() = LlaveUA(
        REGION,
        ZONE, Constant.EMPTY_STRING, -1
    )

    companion object {
        private const val REGION = "06"
        private const val ZONE = "0620"
        private const val CURRENCY_WITH_AMOUNT = "$ 500,000"
        private const val TITLE_NET_SALE_BILLING = "Title Net Sale"
        private const val TITLE_ORDERS_BILLING = "Title Billing"
        private const val DESCRIPTION_NET_SALE_ACHIEVED = "test superaste test"
        private const val DESCRIPTION_NET_SALE_REACHED = "test lograste test"
        private const val DESCRIPTION_NET_SALE_NOT_REACHED = "test te faltan test"
    }
}
