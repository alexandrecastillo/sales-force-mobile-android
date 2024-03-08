@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.kpis.features.feature.features.saleorders.se

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.SalesOrdersMockDataHelper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.mappers.SaleOrdersMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrderContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders.SaleOrdersUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SaleOrdersDetailModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se.SaleOrdersDetailSeViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se.SaleOrdersDetailSeViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se.mapper.SaleOrderSeMapper
import biz.belcorp.salesforce.modules.kpis.utils.SaleOrdersMock.campaignMock
import biz.belcorp.salesforce.modules.kpis.utils.SaleOrdersMock.uaKeyMock
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SaleOrderDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: SaleOrdersDetailSeViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val saleOrdersUseCase = mockk<SaleOrdersUseCase>()
    private val mapper = SaleOrdersMapper()
    private val mapperSe = mockk<SaleOrderSeMapper>()

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = SaleOrdersDetailSeViewModel(saleOrdersUseCase, mapperSe)
    }

    @Test
    fun `test getSaleOrdersSe`() = runBlockingTest {
        coEvery { saleOrdersUseCase.getSaleOrder(any()) } returns getData()
        coEvery { mapperSe.map(any()) } returns getDataModel()
        viewModel.getSaleOrder(uaKeyMock)
        viewModel.viewState.getOrAwaitValue()
        viewModel.viewState.value shouldBeInstanceOf (SaleOrdersDetailSeViewState.Success::class)
        val response = viewModel.viewState.value as SaleOrdersDetailSeViewState.Success
        response.model.items shouldNotBe emptyList()
    }

    private fun getData(): SaleOrderContainer {
        val data =
            SalesOrdersMockDataHelper.getSalesOrdersEntities(uaKeyMock, campaignMock.codigo, mapper)
                .first()
        return SaleOrderContainer(
            saleOrdersIndicator = data,
            currencySymbol = "S/",
            isBilling = false,
            isBright = false
        )
    }

    private fun getDataModel(): SaleOrdersDetailModel {
        val items = SalesOrdersMockDataHelper.getListModel()
        return SaleOrdersDetailModel(
            title = EMPTY_STRING,
            items = items
        )
    }
}
