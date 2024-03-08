package biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.saleorders

import biz.belcorp.mobile.components.design.indicatorgoalbar.IndicatorGoalBar
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.EmojiCode.MUSCLE
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.SaleOrdersTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiViewType
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiSaleOrdersMultiProfileBillingModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiSaleOrdersMultiProfileSaleModel

class KpiSaleOrdersMultiProfileMapper(private val textResolver: SaleOrdersTextResolver) {

    fun createKpiSaleOrders(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        return if (isSale) createKpiSaleOrdersSale(this)
        else createKpiSaleOrdersBilling(this)
    }

    private fun createKpiSaleOrdersSale(kpiContainer: KpiContainer): KpiModel =
        with(kpiContainer) {
            val actual =
                saleOrders.currentData ?: SaleOrdersIndicator(campaign = saleOrders.currentCampaign)
            val currencySymbol = configuration.currencySymbol
            return KpiSaleOrdersMultiProfileSaleModel(
                code = NUMBER_TWO,
                iconRes = R.drawable.ic_kpis_sale_orders_sales,
                iconColor = R.color.colorIcon,
                backgroundColor = R.color.colorSaleOrders,
                header = textResolver.formatSaleOrdersHeader(),
                type = KpiViewType.KPI_TYPE_SALE_ORDERS_MULTIPROFILE_SALE,
                order = NUMBER_TWO,
                isThirdPerson = isThirdPerson,
                title = textResolver.formatTitle(
                    actual.campaign.takeLastTwo(),
                    isThirdPerson = isThirdPerson
                ),
                descriptionFirst = textResolver.formatNetSaleDescriptionMultiProfile(
                    currencySymbol,
                    actual.netSaleGoal.formatTruncateIntThousandsLabel()
                ),
                descriptionSecond = textResolver.formatOrdersDescriptionMultiProfile(
                    actual.ordersGoal,
                    createEmoji(MUSCLE)
                )
            )
        }

    private fun createKpiSaleOrdersBilling(kpiContainer: KpiContainer): KpiModel =
        with(kpiContainer) {
            val actual =
                saleOrders.currentData ?: SaleOrdersIndicator(campaign = saleOrders.currentCampaign)
            val currencySymbol = configuration.currencySymbol
            return KpiSaleOrdersMultiProfileBillingModel(
                code = NUMBER_TWO,
                iconRes = R.drawable.ic_kpis_sale_orders_sales,
                iconColor = R.color.colorIcon,
                backgroundColor = R.color.colorSaleOrders,
                header = textResolver.formatSaleOrdersHeader(),
                type = KpiViewType.KPI_TYPE_SALE_ORDERS_MULTIPROFILE_BILLING,
                order = NUMBER_TWO,
                isThirdPerson = isThirdPerson,
                title = getTitle(actual, isThirdPerson),
                goalSale = createIndicatorGoalBar(
                    IndicatorGoalMultiProfileParams(actual, currencySymbol, false, isThirdPerson)
                ),
                goalOrders = createIndicatorGoalBar(
                    IndicatorGoalMultiProfileParams(actual, currencySymbol, true, isThirdPerson)
                )
            )
        }

    private fun getGoalState(params: IndicatorGoalMultiProfileParams): Int = with(params) {
        return if (useOrders) model.pendingOrderGoal else model.pendingNetSaleGoal
    }

    private fun getTitle(model: SaleOrdersIndicator, isThirdPerson: Boolean): String =
        with(model) {
            return when {
                indicatorsSuccessful -> textResolver.formatSuccessTitleMultiProfileBilling(
                    isThirdPerson = isThirdPerson
                )
                indicatorsReached -> textResolver.formatReachedTitleMultiProfileBilling(
                    isThirdPerson = isThirdPerson
                )
                else -> textResolver.formatSaleOrdersTitleBilling(isThirdPerson = isThirdPerson)
            }
        }

    private fun createIndicatorGoalBar(params: IndicatorGoalMultiProfileParams):
        IndicatorGoalBar.Model = with(params) {
        val goal = getGoalState(this)
        return when {
            goal.isPositive() -> goalDefault(this)
            goal.isNegative() -> goalExceeded(this)
            else -> goalSuccess(this)
        }
    }

    private fun goalDefault(
        params: IndicatorGoalMultiProfileParams
    ): IndicatorGoalBar.Model = with(params) {
        val currentTarget =
            if (useOrders) textResolver.formatQuantityOrders(model.orders)
            else textResolver.formatCurrencyAmount(
                currencySymbol,
                model.netSale.formatTruncateIntThousandsLabel()
            )
        val currentProgress = if (useOrders) model.orders else model.netSale.truncateLowerInt()
        val maxProgress =
            if (useOrders) model.ordersGoal else model.netSaleGoal.truncateLowerInt()
        val goalDescription =
            if (useOrders) getOrdersGoalDescription(model.ordersGoal)
            else getNetSaleGoalDescription(currencySymbol, model.netSaleGoal)

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
        params: IndicatorGoalMultiProfileParams
    ): IndicatorGoalBar.Model = with(params) {
        val currentTarget =
            if (useOrders) textResolver.formatQuantityOrders(model.orders)
            else textResolver.formatCurrencyAmount(
                currencySymbol,
                model.netSale.formatTruncateIntThousandsLabel()
            )
        val currentProgress = if (useOrders) model.orders else model.netSale.truncateLowerInt()
        val maxProgress =
            if (useOrders) model.ordersGoal else model.netSaleGoal.truncateLowerInt()
        val goalDescription =
            if (useOrders) getOrdersGoalDescription(model.ordersGoal)
            else getNetSaleGoalDescription(currencySymbol, model.netSaleGoal)

        return IndicatorGoalBar.Model(
            currentTarget = currentTarget,
            currentProgress = currentProgress,
            maxProgress = maxProgress,
            enableAnimation = false,
            goalDescription = goalDescription,
            type = IndicatorGoalBar.GoalType.SUCCESS
        )
    }

    private fun goalSuccess(
        params: IndicatorGoalMultiProfileParams
    ): IndicatorGoalBar.Model = with(params) {
        val currentTarget =
            if (useOrders) textResolver.formatQuantityOrders(model.orders)
            else textResolver.formatCurrencyAmount(
                currencySymbol,
                model.netSale.formatTruncateIntThousandsLabel()
            )
        val currentProgress = if (useOrders) model.orders else model.netSale.truncateLowerInt()
        val maxProgress =
            if (useOrders) model.ordersGoal else model.netSaleGoal.truncateLowerInt()
        val goalDescription =
            if (useOrders) getOrdersGoalDescription(model.ordersGoal)
            else getNetSaleGoalDescription(currencySymbol, model.netSaleGoal)

        return IndicatorGoalBar.Model(
            currentTarget = currentTarget,
            currentProgress = if (currentProgress.isZero()) NUMBER_ONE else currentProgress,
            maxProgress = if (maxProgress.isZero()) NUMBER_ONE else maxProgress,
            enableAnimation = false,
            goalDescription = goalDescription,
            type = IndicatorGoalBar.GoalType.SUCCESS
        )
    }

    private fun getNetSaleGoalDescription(currency: String, amount: Double) =
        textResolver.formatNetSaleGoalMultiProfile(
            currency,
            amount.formatTruncateIntThousandsLabel()
        )

    private fun getOrdersGoalDescription(orders: Int) =
        textResolver.formatOrdersGoalDescription(orders.formatWithThousands())

}
