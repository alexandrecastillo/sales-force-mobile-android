@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.modules.kpis.features.feature.features.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.configuration.MainBrandType
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.createEmoji
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiData
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.dashboard.KpiDashboardUseCase
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.RetentionCapiTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.CollectionTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.NewCycleTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.SaleOrdersTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiDashboardTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiDashboardViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiDashboardViewState
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiViewType
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.KpiDashboardMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.capitalization.KpiRetentionCapiMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.collection.KpiCollectionMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.newcycle.KpiNewCycleMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.saleorders.KpiSaleOrdersMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiDashboardParams
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiSaleOrdersBillingModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiSaleOrdersSaleModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.junit.*

@Ignore("No funciona con Sonar")
class KpiDashboardViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val businessPartner = mockk<BusinessPartner>()
    private val campaign = mockk<Campania>()

    private val useCase = mockk<KpiDashboardUseCase>()

    private val saleOrderTextResolver = mockk<SaleOrdersTextResolver>(relaxed = true)
    private val collectionTextResolver = mockk<CollectionTextResolver>(relaxed = true)
    private val newCycleTextResolver = mockk<NewCycleTextResolver>(relaxed = true)
    private val retentionCapiTextResolver = mockk<RetentionCapiTextResolver>(relaxed = true)
    private val dashboardTextResolver = mockk<KpiDashboardTextResolver>(relaxed = true)

    private val saleOrderMapper = KpiSaleOrdersMapper(saleOrderTextResolver)
    private val collectionMapper = KpiCollectionMapper(collectionTextResolver)
    private val newCycleMapper = KpiNewCycleMapper(newCycleTextResolver)
    private val retentionCapiMapper = KpiRetentionCapiMapper(retentionCapiTextResolver)
    private lateinit var mapper: KpiDashboardMapper
    private lateinit var viewModel: KpiDashboardViewModel
    private val observer = mockk<Observer<KpiDashboardViewState>>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        every { dashboardTextResolver.formatTitleKpisDashboard(*anyVararg()) } returns DEFAULT_TEXT

        every { businessPartner.uaKey } returns createUaKey()
        every { businessPartner.role } returns Rol.SOCIA_EMPRESARIA

        every { campaign.codigo } returns "201918"
        every { campaign.esPrimerDiaFacturacion } returns false
        every { campaign.nombreCorto } returns "C18"

        mockkStatic(EMOJI_PACKAGE)
        every { createEmoji(any()) } returns DEFAULT_TEXT_EMOJI

        configureSaleOrders()
        configureCollection()
        configureNewCycle()
        configureCapitalization()

        coEvery { useCase.getKpiInformation(any()) } returns KpiContainer(
            getSalesOrdersKpiData(),
            getCollectionsKpiData(),
            getNewCycleKpiData(),
            getCapitalizationKpiData(),
            createConfiguration(false),
            false,
            Rol.SOCIA_EMPRESARIA,
            businessPartner,
            campaign,
            false,
            Rol.SOCIA_EMPRESARIA
        )

        mapper = KpiDashboardMapper(
            saleOrderMapper,
            collectionMapper,
            newCycleMapper,
            retentionCapiMapper,
            dashboardTextResolver
        )
        viewModel = KpiDashboardViewModel(mockk(), mapper, mockk(), mockk())
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `test Kpi Dashboard state Success`() = runBlockingTest {
        every { campaign.periodo } returns Campania.Periodo.VENTA
        viewModel.getKpiInformation(createParams())
        viewModel.viewState.getOrAwaitValue()
        verify { observer.onChanged(any<KpiDashboardViewState.Success>()) }
    }

    @Test
    fun `test Kpi Sale Orders in Dashboard for SE period Sale`() = runBlockingTest {
        every { campaign.periodo } returns Campania.Periodo.VENTA
        viewModel.getKpiInformation(createParams())
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value as KpiDashboardViewState.Success
        val model =
            state.model.kpis.firstOrNull { it.type == KpiViewType.KPI_TYPE_SALE_ORDERS_SALE }
        with(requireNotNull(model)) {
            code shouldEqual Constant.NUMBER_TWO
            type shouldEqual KpiViewType.KPI_TYPE_SALE_ORDERS_SALE
            iconRes shouldEqual R.drawable.ic_kpis_sale_orders_sales
            iconColor shouldEqual R.color.colorIcon
            backgroundColor shouldEqual R.color.colorSaleOrders
            order shouldEqual Constant.NUMBER_TWO
            header shouldEqual SALE_ORDERS_HEADER
            this shouldBeInstanceOf KpiSaleOrdersSaleModel::class
            (this as KpiSaleOrdersSaleModel).title shouldEqual SALE_ORDERS_SALE_TITLE
        }
    }

    @Test
    fun `test Kpi Sale Orders in Dashboard for SE period Billing day 1`() = runBlockingTest {
        every { campaign.esPrimerDiaFacturacion } returns true
        `test Kpi Sale Orders in Dashboard for SE period Sale`()
    }

    @Test
    fun `test Kpi Sale Orders in Dashboard for SE period Billing day 2`() = runBlockingTest {
        every { campaign.periodo } returns Campania.Periodo.FACTURACION
        every { campaign.esPrimerDiaFacturacion } returns false
        viewModel.getKpiInformation(createParams())
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value as KpiDashboardViewState.Success
        val model =
            state.model.kpis.firstOrNull { it.type == KpiViewType.KPI_TYPE_SALE_ORDERS_BILLING }
        with(requireNotNull(model)) {
            code shouldEqual Constant.NUMBER_TWO
            type shouldEqual KpiViewType.KPI_TYPE_SALE_ORDERS_BILLING
            iconRes shouldEqual R.drawable.ic_kpis_sale_orders_sales
            iconColor shouldEqual R.color.colorIcon
            backgroundColor shouldEqual R.color.colorSaleOrders
            order shouldEqual Constant.NUMBER_TWO
            header shouldEqual SALE_ORDERS_HEADER
            this shouldBeInstanceOf KpiSaleOrdersBillingModel::class
            (this as KpiSaleOrdersBillingModel).title shouldEqual SALE_ORDERS_BILLING_TITLE
            this.subtitleFirst shouldEqual SALE_ORDERS_BILLING_CATALOG_SALE
            this.descriptionFirst shouldEqual CURRENCY_AMOUNT
            this.goalBar shouldNotEqual null
        }
    }

    @Test
    fun `test Kpi Sale Orders in Dashboard for SE period Sale Bright PyD`() = runBlockingTest {
        every { campaign.periodo } returns Campania.Periodo.VENTA
        coEvery { useCase.getKpiInformation(any()) } returns KpiContainer(
            getSalesOrdersKpiData(),
            getCollectionsKpiData(),
            getNewCycleKpiData(),
            getCapitalizationKpiData(),
            createConfiguration(true),
            true,
            Rol.SOCIA_EMPRESARIA,
            businessPartner,
            campaign,
            false,
            Rol.SOCIA_EMPRESARIA
        )
        every { businessPartner.levelType } returns BusinessPartner.Level.Platinum
        viewModel.getKpiInformation(createParams())
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value as KpiDashboardViewState.Success
        val model =
            state.model.kpis.firstOrNull { it.type == KpiViewType.KPI_TYPE_SALE_ORDERS_SALE }
        with(requireNotNull(model)) {
            code shouldEqual Constant.NUMBER_TWO
            type shouldEqual KpiViewType.KPI_TYPE_SALE_ORDERS_SALE
            iconRes shouldEqual R.drawable.ic_kpis_sale_orders_sales
            iconColor shouldEqual R.color.colorIcon
            backgroundColor shouldEqual R.color.colorSaleOrders
            order shouldEqual Constant.NUMBER_TWO
            header shouldEqual SALE_ORDERS_HEADER
            this shouldBeInstanceOf KpiSaleOrdersSaleModel::class
        }
    }

    @Test
    fun `test Kpi Sale Orders in Dashboard for SE period Billing Bright PyD 1`() = runBlockingTest {
        every { campaign.periodo } returns Campania.Periodo.FACTURACION
        every { campaign.esPrimerDiaFacturacion } returns true
        coEvery { useCase.getKpiInformation(any()) } returns KpiContainer(
            getSalesOrdersKpiData(),
            getCollectionsKpiData(),
            getNewCycleKpiData(),
            getCapitalizationKpiData(),
            createConfiguration(true),
            true,
            Rol.SOCIA_EMPRESARIA,
            businessPartner,
            campaign,
            false,
            Rol.SOCIA_EMPRESARIA
        )
        every { businessPartner.levelType } returns BusinessPartner.Level.Platinum
        viewModel.getKpiInformation(createParams())
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value as KpiDashboardViewState.Success
        val model =
            state.model.kpis.firstOrNull { it.type == KpiViewType.KPI_TYPE_SALE_ORDERS_SALE }
        with(requireNotNull(model)) {
            code shouldEqual Constant.NUMBER_TWO
            type shouldEqual KpiViewType.KPI_TYPE_SALE_ORDERS_SALE
            iconRes shouldEqual R.drawable.ic_kpis_sale_orders_sales
            iconColor shouldEqual R.color.colorIcon
            backgroundColor shouldEqual R.color.colorSaleOrders
            order shouldEqual Constant.NUMBER_TWO
            header shouldEqual SALE_ORDERS_HEADER
            this shouldBeInstanceOf KpiSaleOrdersSaleModel::class
        }
    }

    @Test
    fun `test Kpi Sale Orders in Dashboard for SE period Billing Bright PyD 2`() = runBlockingTest {
        every { campaign.periodo } returns Campania.Periodo.FACTURACION
        every { campaign.esPrimerDiaFacturacion } returns false
        coEvery { useCase.getKpiInformation(any()) } returns KpiContainer(
            getSalesOrdersKpiData(),
            getCollectionsKpiData(),
            getNewCycleKpiData(),
            getCapitalizationKpiData(),
            createConfiguration(true),
            true,
            Rol.SOCIA_EMPRESARIA,
            businessPartner,
            campaign,
            false,
            Rol.SOCIA_EMPRESARIA
        )
        every { businessPartner.levelType } returns BusinessPartner.Level.Diamond
        viewModel.getKpiInformation(createParams())
        viewModel.viewState.getOrAwaitValue()
        val state = viewModel.viewState.value as KpiDashboardViewState.Success
        val model =
            state.model.kpis.firstOrNull { it.type == KpiViewType.KPI_TYPE_SALE_ORDERS_BILLING }
        with(requireNotNull(model)) {
            code shouldEqual Constant.NUMBER_TWO
            type shouldEqual KpiViewType.KPI_TYPE_SALE_ORDERS_BILLING
            iconRes shouldEqual R.drawable.ic_kpis_sale_orders_sales
            iconColor shouldEqual R.color.colorIcon
            backgroundColor shouldEqual R.color.colorSaleOrders
            order shouldEqual Constant.NUMBER_TWO
            header shouldEqual SALE_ORDERS_HEADER
            this shouldBeInstanceOf KpiSaleOrdersBillingModel::class
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    private fun configureSaleOrders() {
        every { saleOrderTextResolver.formatSaleOrdersHeader() } returns SALE_ORDERS_HEADER
        every { saleOrderTextResolver.formatSuccessTitleBilling(isThirdPerson = false) } returns SALE_ORDERS_BILLING_TITLE
        every { saleOrderTextResolver.formatReachedTitleBilling(isThirdPerson = false) } returns SALE_ORDERS_BILLING_TITLE
        every { saleOrderTextResolver.formatDefaultTitleBilling(isThirdPerson = false) } returns SALE_ORDERS_BILLING_TITLE
        every { saleOrderTextResolver.getCatalogSaleTitle() } returns SALE_ORDERS_BILLING_CATALOG_SALE
        every { saleOrderTextResolver.formatCurrencyAmount(*anyVararg()) } returns CURRENCY_AMOUNT
        every { saleOrderTextResolver.getOrdersTitle() } returns EMPTY_STRING
        every { saleOrderTextResolver.formatQuantityOrders(any()) } returns QUANTITY
        every { saleOrderTextResolver.formatCatalogSaleDescription(*anyVararg()) } returns SALE_ORDERS_CATALOG_DESCRIPTION
        every { saleOrderTextResolver.formatDescriptionOrdersBilled(any()) } returns EMPTY_STRING
        every { saleOrderTextResolver.formatOrdersGoalDescription(*anyVararg()) } returns SALE_ORDERS_ORDERS_DESCRIPTION
        every {
            saleOrderTextResolver.formatTitle(*anyVararg(), isThirdPerson = false)
        } returns SALE_ORDERS_SALE_TITLE
        every { saleOrderTextResolver.formatDescriptionOrders(*anyVararg()) } returns EMPTY_STRING
        every {
            saleOrderTextResolver.formatHomeSaleOrderSaleTitle(*anyVararg(), isThirdPerson = false)
        } returns DEFAULT_TEXT
        every {
            saleOrderTextResolver.formatSaleOrdersSaleDescriptionGZ(*anyVararg())
        } returns DEFAULT_TEXT
        every { saleOrderTextResolver.formatHomeSaleOrderCatalogSale(*anyVararg()) } returns DEFAULT_TEXT
        every { saleOrderTextResolver.formatNetSaleDescriptionMultiProfile(*anyVararg()) }
        every { saleOrderTextResolver.formatOrdersDescriptionMultiProfile(any(), any()) }
        every { saleOrderTextResolver.formatSuccessTitleMultiProfileBilling(any()) } returns EMPTY_STRING
        every { saleOrderTextResolver.formatReachedTitleMultiProfileBilling(any()) } returns EMPTY_STRING
        every {
            saleOrderTextResolver.formatSaleOrdersTitleBilling(*anyVararg(), isThirdPerson = false)
        } returns EMPTY_STRING
        every { saleOrderTextResolver.formatNetSaleGoalMultiProfile(*anyVararg()) } returns EMPTY_STRING
        every { saleOrderTextResolver.formatDescriptionCatalogSale(*anyVararg()) } returns EMPTY_STRING
    }

    private fun configureCollection() {
        every { collectionTextResolver.formatHeader() } returns COLLECTION_HEADER
        every { collectionTextResolver.formatHeaderMultiProfile() } returns COLLECTION_HEADER
        every {
            collectionTextResolver.formatTitle(*anyVararg(), isThirdPerson = false)
        } returns COLLECTION_TITLE
        every { collectionTextResolver.formatDescriptionRecovery(*anyVararg()) } returns EMPTY_STRING
        every { collectionTextResolver.formatDescriptionChargedOrders(*anyVararg()) } returns EMPTY_STRING
    }

    private fun configureNewCycle() {
        every { newCycleTextResolver.formatNewCycleHeader() } returns EMPTY_STRING
        every {
            newCycleTextResolver.formatSaleTitle(*anyVararg(), isThirdPerson = false)
        } returns EMPTY_STRING
        every {
            newCycleTextResolver.formatConsultants(*anyVararg(), quantity = NUMBER_ZERO)
        } returns EMPTY_STRING
        every {
            newCycleTextResolver.formatConsultantsHighValue(*anyVararg(), quantity = NUMBER_ZERO)
        } returns EMPTY_STRING
        every { newCycleTextResolver.formatBillingTitle(isThirdPerson = false) } returns EMPTY_STRING
        every {
            newCycleTextResolver.formatConsultantsBilling(*anyVararg(), quantity = NUMBER_ZERO)
        } returns EMPTY_STRING
        every {
            newCycleTextResolver.formatConsultantsHighValueBilling(
                *anyVararg(),
                quantity = NUMBER_ZERO
            )
        } returns EMPTY_STRING
    }

    private fun configureCapitalization() {
        every { retentionCapiTextResolver.formatHeader() } returns EMPTY_STRING
        every {
            retentionCapiTextResolver.formatTitle(*anyVararg(), isThirdPerson = false)
        } returns EMPTY_STRING
        every { retentionCapiTextResolver.formatSubTitleEntries() } returns EMPTY_STRING
        every { retentionCapiTextResolver.formatDescriptionEntries(NUMBER_ZERO) } returns EMPTY_STRING
        every { retentionCapiTextResolver.formatSubTitleRetention() } returns EMPTY_STRING
        every {
            retentionCapiTextResolver.formatDescriptionRetention(NUMBER_ZERO, EMPTY_STRING)
        } returns EMPTY_STRING
        every {
            retentionCapiTextResolver.formatSubTitleEntriesBilling(isThirdPerson = false)
        } returns EMPTY_STRING
        every { retentionCapiTextResolver.formatDescriptionEntries(NUMBER_ZERO) } returns EMPTY_STRING
        every { retentionCapiTextResolver.formatSubTitleBilling(isThirdPerson = false) } returns EMPTY_STRING
        every {
            retentionCapiTextResolver.formatDescriptionRetentionBilling(*anyVararg())
        } returns EMPTY_STRING
        every { retentionCapiTextResolver.formatDescription(*anyVararg()) } returns EMPTY_STRING
        every {
            retentionCapiTextResolver.formatDescriptionBillingMultiProfile(isThirdPerson = false)
        } returns EMPTY_STRING
        every {
            retentionCapiTextResolver.formatCapitalizationProjected(*anyVararg())
        } returns EMPTY_STRING
    }

    private fun createParams() = KpiDashboardParams(
        role = Rol.SOCIA_EMPRESARIA,
        uaKey = createUaKey(),
        person = null
    )

    private fun createUaKey() =
        LlaveUA("06", "0620", "A", -1)

    private fun createConfiguration(isPdv: Boolean) =
        Configuration(
            country = "CR",
            isPdv = isPdv,
            phoneCode = "+506",
            currencySymbol = "¢",
            countryName = "Costa Rica",
            tippingPoint = 0,
            minimalOrderAmount = 0,
            mainBrand = MainBrandType.ESIKA,
            isGanaMas = false,
            isOnlineStoreEnabled = false,
            onlineStoreTitle = EMPTY_STRING,
            onlineStoreType = EMPTY_STRING,
            updateType = EMPTY_STRING,
            flagMto = Constant.NUMERO_UNO,
            flagShowProactive = false
        )

    private fun createSaleOrdersIndicator(size: Int): List<SaleOrdersIndicator> {
        val elements = arrayListOf<SaleOrdersIndicator>()
        for (i in 0 until size) {
            val element = SaleOrdersIndicator(
                campaign = getCampaign(i),
                region = "09",
                zone = "0906",
                section = "A",
                netSale = ZERO_DECIMAL,
                netSaleGoal = ZERO_DECIMAL,
                fulfillmentNetSalePercentage = ZERO_DECIMAL,
                catalogSale = ZERO_DECIMAL,
                catalogSaleGoal = ZERO_DECIMAL,
                fulfillmentCatalogSalesPercentage = ZERO_DECIMAL,
                orders = NUMBER_ZERO,
                ordersGoal = NUMBER_ZERO,
                highValueOrders = NUMBER_ZERO,
                lowValueOrders = NUMBER_ZERO,
                averageAmount = ZERO_DECIMAL,
                fulfillmentOrderPercentage = ZERO_DECIMAL,
                fulfillmentOrderAveragePercentage = ZERO_DECIMAL,
                activityPercentage = ZERO_DECIMAL,
                activityGoal = ZERO_DECIMAL,
                activityPegs = NUMBER_ZERO,
                fulfillmentActivityPercentage = ZERO_DECIMAL,
                activesInitials = NUMBER_ZERO,
                activesFinals = NUMBER_ZERO,
                activesFinalsLastYear = NUMBER_ZERO,
                activesRetentionPercentage = ZERO_DECIMAL,
                activesRetentionGoal = ZERO_DECIMAL,
                fulfillmentRetentionPercentage = ZERO_DECIMAL,
                salesRange = emptyList(),
                ordersRange = emptyList(),
                averageAmountGoal = ZERO_DECIMAL
            )
            elements.add(element)
        }
        return elements
    }

    private fun getCampaign(i: Int): String {
        return if (i == 0) "202005" else "202004"
    }

    private fun createCollectionsIndicator() = listOf<CollectionIndicator>()

    private fun createNewCycleIndicator() = listOf<NewCycleIndicator>()

    private fun createCapitalizationIndicator() = listOf<CapitalizationIndicator>()

    private fun getSalesOrdersKpiData(): KpiData<SaleOrdersIndicator> {
        val list = createSaleOrdersIndicator(2)
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, "202005", "202004")
    }

    private fun getCollectionsKpiData(): KpiData<CollectionIndicator> {
        val list = createCollectionsIndicator()
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, "202005", "202004")
    }

    private fun getNewCycleKpiData(): KpiData<NewCycleIndicator> {
        val list = createNewCycleIndicator()
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, "202005", "202004")
    }

    private fun getCapitalizationKpiData(): KpiData<CapitalizationIndicator> {
        val list = createCapitalizationIndicator()
        val kpiData = list.groupBy { it.campaign }
        return KpiData(kpiData, "202005", "202004")
    }

    companion object {
        private const val EMOJI_PACKAGE = "biz.belcorp.salesforce.core.utils.EmojiUtilsKt"
        private const val DEFAULT_TEXT_EMOJI = "emoji"
        private const val DEFAULT_TEXT = "message"
        private const val SALE_ORDERS_SALE_TITLE = "En C09 tu meta es"
        private const val SALE_ORDERS_BILLING_TITLE = "Vas logrando"
        private const val SALE_ORDERS_BILLING_CATALOG_SALE = "Venta Catálogo"
        private const val SALE_ORDERS_HEADER = "Pedidos y Venta"
        private const val CURRENCY_AMOUNT = "S/. 100.00"
        private const val QUANTITY = "quantity"
        private const val SALE_ORDERS_CATALOG_DESCRIPTION = "catalog_description"
        private const val SALE_ORDERS_ORDERS_DESCRIPTION = "orders_description"
        private const val COLLECTION_HEADER = "Ganancia y Saldo"
        private const val COLLECTION_TITLE = "En C09 vas logrando"
    }
}
