package biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.saleorders

import biz.belcorp.mobile.components.design.indicatorgoalbar.IndicatorGoalBar
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.CountryISO
import biz.belcorp.salesforce.core.constants.EmojiCode
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.SaleOrdersTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiViewType
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiSaleOrdersBillingModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiSaleOrdersSaleModel
import biz.belcorp.salesforce.modules.kpis.utils.Constant

class KpiSaleOrdersSEMapper(
    private val textResolver: SaleOrdersTextResolver
) {

    fun createKpiSaleOrders(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        return when (configuration.isPdv) {
            true -> createKpiSePdv(kpiContainer)
            else -> createKpiSE(kpiContainer)
        }
    }

    private fun createKpiSePdv(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        return if (person == null) createKpiSE(this)
        else createKpiSeBrightLevel(this)
    }

    private fun createKpiSE(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        return if (isSale) createKpiSaleOrdersSE(this)
        else createKpiSEBilling(this)
    }

    private fun createKpiSeBrightLevel(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        return when {
            isBright -> createKpiSEBright(kpiContainer)
            else -> createKpiSE(kpiContainer)
        }
    }

    private fun createKpiSaleOrdersSE(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        val actual =
            saleOrders.currentData ?: SaleOrdersIndicator(campaign = saleOrders.currentCampaign)
        return KpiSaleOrdersSaleModel(
            code = NUMBER_TWO,
            iconRes = R.drawable.ic_kpis_sale_orders_sales,
            iconColor = R.color.colorIcon,
            backgroundColor = R.color.colorSaleOrders,
            header = textResolver.formatSaleOrdersHeader(),
            type = KpiViewType.KPI_TYPE_SALE_ORDERS_SALE,
            order = NUMBER_TWO,
            isThirdPerson = isThirdPerson,
            title = textResolver.formatTitle(
                actual.campaign.takeLastTwo(),
                isThirdPerson = isThirdPerson
            ),
            description = textResolver.formatDescriptionOrders(
                actual.ordersGoal.formatWithThousands(),
                createEmoji(EmojiCode.MUSCLE)
            )
        )
    }

    private fun createKpiSEBright(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        return if (isSale) createKpiSEBrightSale(this)
        else createKpiSEBrightBilling(this)
    }

    private fun createKpiSEBilling(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        val actual =
            saleOrders.currentData ?: SaleOrdersIndicator(campaign = saleOrders.currentCampaign)
        val currencySymbol = configuration.currencySymbol
        val params = IndicatorGoalSEParams(
            actual,
            currencySymbol,
            isBright,
            isThirdPerson,
            configuration.country
        )
        return KpiSaleOrdersBillingModel(
            code = NUMBER_TWO,
            iconRes = R.drawable.ic_kpis_sale_orders_sales,
            iconColor = R.color.colorIcon,
            backgroundColor = R.color.colorSaleOrders,
            header = textResolver.formatSaleOrdersHeader(),
            type = KpiViewType.KPI_TYPE_SALE_ORDERS_BILLING,
            order = NUMBER_TWO,
            isThirdPerson = isThirdPerson,
            title = createIndicatorBillingTitle(params),
            goalBar = createIndicatorGoalBar(params),
            subtitleFirst = textResolver.getCatalogSaleTitle(),
            descriptionFirst = getCatalogSaleDescription(currencySymbol, actual.catalogSale)
        )
    }

    private fun createKpiSEBrightBilling(kpiContainer: KpiContainer): KpiModel =
        with(kpiContainer) {
            val actual =
                saleOrders.currentData ?: SaleOrdersIndicator(campaign = saleOrders.currentCampaign)
            val currencySymbol = configuration.currencySymbol
            val params = IndicatorGoalSEParams(
                actual,
                currencySymbol,
                isBright,
                isThirdPerson,
                configuration.country
            )
            val output = KpiSaleOrdersBillingModel(
                code = NUMBER_TWO,
                iconRes = R.drawable.ic_kpis_sale_orders_sales,
                iconColor = R.color.colorIcon,
                backgroundColor = R.color.colorSaleOrders,
                header = textResolver.formatSaleOrdersHeader(),
                type = KpiViewType.KPI_TYPE_SALE_ORDERS_BILLING,
                order = NUMBER_TWO,
                isThirdPerson = isThirdPerson,
                title = createIndicatorBillingTitle(params),
                goalBar = createIndicatorGoalBar(params),
                subtitleFirst = textResolver.getOrdersTitle(),
                descriptionFirst = getDescriptionOrdersBilled(actual.orders)
            )

            if (kpiContainer.configuration.country == CountryISO.COSTA_RICA) {
                output.subtitleFirst = ""
                output.descriptionFirst = "${
                    textResolver.formatDescriptionCatalogSale(
                        currencySymbol,
                        actual.catalogSaleGoal.formatTruncateIntThousandsLabel(),
                        createEmoji(EmojiCode.MUSCLE)
                    )
                }"
            }

            return output
        }

    private fun createKpiSEBrightSale(kpiContainer: KpiContainer): KpiModel {
        val actual =
            kpiContainer.saleOrders.currentData
                ?: SaleOrdersIndicator(campaign = kpiContainer.saleOrders.currentCampaign)
        val currencySymbol = kpiContainer.configuration.currencySymbol

        val output = KpiSaleOrdersSaleModel(
            code = NUMBER_TWO,
            iconRes = R.drawable.ic_kpis_sale_orders_sales,
            iconColor = R.color.colorIcon,
            backgroundColor = R.color.colorSaleOrders,
            header = textResolver.formatSaleOrdersHeader(),
            type = KpiViewType.KPI_TYPE_SALE_ORDERS_SALE,
            order = NUMBER_TWO,
            isThirdPerson = kpiContainer.isThirdPerson,
            title = textResolver.formatTitle(
                actual.campaign.takeLastTwo(),
                isThirdPerson = kpiContainer.isThirdPerson
            ),
            description = textResolver.formatDescriptionCatalogSale(
                currencySymbol,
                actual.catalogSaleGoal.formatTruncateIntThousandsLabel(),
                createEmoji(EmojiCode.MUSCLE)
            )
        )

        if (kpiContainer.configuration.country == CountryISO.COSTA_RICA) {
            output.description = "\n${
                textResolver.formatDescriptionOrders(
                    actual.ordersGoal.formatWithThousands(),
                    createEmoji(EmojiCode.MUSCLE)
                )
            }"
        }

        return output
    }

    private fun getGoalState(params: IndicatorGoalSEParams): Int = with(params) {
        return if (Constant.PAIS_CAMBIO_METAS.contains(this.iso.toUpperCase())) {
            model.pendingOrderGoal
        } else {
            if (isBright) model.pendingCatalogSaleGoal.truncateLowerInt() else model.pendingOrderGoal
        }
    }

    private fun createIndicatorBillingTitle(params: IndicatorGoalSEParams): String = with(params) {
        val goal = getGoalState(this)
        return when {
            goal.isPositive() -> textResolver.formatDefaultTitleBilling(isThirdPerson = isThirdPerson)
            goal.isNegative() -> textResolver.formatSuccessTitleBilling(isThirdPerson = isThirdPerson)
            else -> textResolver.formatReachedTitleBilling(isThirdPerson = isThirdPerson)
        }
    }

    private fun createIndicatorGoalBar(params: IndicatorGoalSEParams): IndicatorGoalBar.Model =
        with(params) {
            val goal = getGoalState(this)
            return when {
                goal.isPositive() -> goalDefault(this)
                goal.isNegative() -> goalExceeded(this)
                else -> goalSuccess(this)
            }
        }

    private fun getOrdersDescription(orders: Int) = textResolver.formatQuantityOrders(orders)

    private fun getDescriptionOrdersBilled(amount: Int) =
        textResolver.formatDescriptionOrdersBilled(amount)

    private fun getCatalogSaleDescription(currencySymbol: String, catalogSale: Double): String {
        return textResolver.formatCurrencyAmount(
            currencySymbol,
            catalogSale.formatTruncateIntThousandsLabel()
        )
    }

    private fun getOrdersGoalDescription(orders: Int) =
        textResolver.formatOrdersGoalDescription(orders.toString())

    private fun getCatalogSaleGoalDescription(catalogSale: Double) =
        textResolver.formatCatalogSaleDescription(catalogSale.formatTruncateIntThousandsLabel())

    private fun goalDefault(
        params: IndicatorGoalSEParams
    ): IndicatorGoalBar.Model = with(params) {
        val currentTarget = if (Constant.PAIS_CAMBIO_METAS.contains(params.iso.toUpperCase())) {
            getOrdersDescription(model.orders)
        } else {
            if (isBright) getCatalogSaleDescription(currencySymbol, model.catalogSale)
            else getOrdersDescription(model.orders)
        }

        val currentProgress = if (Constant.PAIS_CAMBIO_METAS.contains(params.iso.toUpperCase())) {
            model.orders
        } else {
            if (isBright) model.catalogSale.truncateLowerInt() else model.orders
        }

        val maxProgress = if (Constant.PAIS_CAMBIO_METAS.contains(params.iso.toUpperCase())) {
            model.ordersGoal
        } else {
            if (isBright) model.catalogSaleGoal.truncateLowerInt() else model.ordersGoal
        }

        val goalDescription = if (Constant.PAIS_CAMBIO_METAS.contains(params.iso.toUpperCase())) {
            getOrdersGoalDescription(model.ordersGoal)
        } else {
            if (isBright) getCatalogSaleGoalDescription(model.catalogSaleGoal)
            else getOrdersGoalDescription(model.ordersGoal)
        }

        return IndicatorGoalBar.Model(
            currentTarget = currentTarget,
            currentProgress = currentProgress,
            maxProgress = maxProgress,
            enableAnimation = false,
            goalDescription = goalDescription,
            type = IndicatorGoalBar.GoalType.NORMAL
        )
    }

    private fun goalExceeded(
        params: IndicatorGoalSEParams
    ): IndicatorGoalBar.Model = with(params) {
        val currentTarget = if (Constant.PAIS_CAMBIO_METAS.contains(params.iso.toUpperCase())) {
            getOrdersDescription(model.orders)
        } else {
            if (isBright) getCatalogSaleDescription(currencySymbol, model.catalogSale)
            else getOrdersDescription(model.orders)
        }

        val currentProgress = if (Constant.PAIS_CAMBIO_METAS.contains(params.iso.toUpperCase())) {
            model.orders
        } else {
            if (isBright) model.catalogSale.truncateLowerInt() else model.orders
        }

        val maxProgress = if (Constant.PAIS_CAMBIO_METAS.contains(params.iso.toUpperCase())) {
            model.ordersGoal
        } else {
            if (isBright) model.catalogSaleGoal.truncateLowerInt() else model.ordersGoal
        }

        val goalDescription = if (Constant.PAIS_CAMBIO_METAS.contains(params.iso.toUpperCase())) {
            getOrdersGoalDescription(model.ordersGoal)
        } else {
            if (isBright) getCatalogSaleGoalDescription(model.catalogSaleGoal)
            else getOrdersGoalDescription(model.ordersGoal)
        }

        return IndicatorGoalBar.Model(
            currentTarget = currentTarget,
            currentProgress = currentProgress,
            maxProgress = if (currentProgress > maxProgress) currentProgress else maxProgress,
            enableAnimation = false,
            goalDescription = goalDescription,
            type = IndicatorGoalBar.GoalType.SUCCESS
        )
    }

    private fun goalSuccess(
        params: IndicatorGoalSEParams
    ): IndicatorGoalBar.Model = with(params) {

        val currentProgress = if (Constant.PAIS_CAMBIO_METAS.contains(params.iso.toUpperCase())) {
            model.orders
        } else {
            if (isBright) model.catalogSale.truncateLowerInt() else model.orders
        }

        val maxProgress = if (Constant.PAIS_CAMBIO_METAS.contains(params.iso.toUpperCase())) {
            model.ordersGoal
        } else {
            if (isBright) model.catalogSaleGoal.truncateLowerInt() else model.ordersGoal
        }

        val goalDescription = if (Constant.PAIS_CAMBIO_METAS.contains(params.iso.toUpperCase())) {
            currencySymbol = ""
            getOrdersGoalDescription(model.ordersGoal)
        } else {
            if (isBright) getCatalogSaleGoalDescription(model.catalogSaleGoal)
            else getOrdersGoalDescription(model.ordersGoal)
        }

        val currentTarget = if (Constant.PAIS_CAMBIO_METAS.contains(params.iso.toUpperCase())) {
            getOrdersDescription(model.orders)
        } else {
            if (isBright) getCatalogSaleDescription(currencySymbol, model.catalogSale)
            else getOrdersDescription(model.orders)
        }

        return IndicatorGoalBar.Model(
            currentTarget = currentTarget,
            currentProgress = if (currentProgress.isZero()) NUMBER_ONE else currentProgress,
            maxProgress = if (maxProgress.isZero()) NUMBER_ONE else maxProgress,
            enableAnimation = false,
            goalDescription = goalDescription,
            type = IndicatorGoalBar.GoalType.SUCCESS
        )
    }

}
