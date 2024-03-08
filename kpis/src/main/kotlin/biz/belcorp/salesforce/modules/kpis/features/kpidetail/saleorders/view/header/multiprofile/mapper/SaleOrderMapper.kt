package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile.mapper

import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.design.indicatorgoalbar.IndicatorGoalBar
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.BLANK_SPACE
import biz.belcorp.salesforce.core.constants.Constant.NEW_LINE
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.mappers.LOW_VALUE_PLUS
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrderMultiProfileContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersOrderRange
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.SaleOrdersTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.*
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.DefaultGridModel
import java.util.*
class SaleOrderMapper(private val textResolver: SaleOrdersTextResolver) {


    fun map(model: SaleOrderMultiProfileContainer): SaleOrdersHeaderModel = with(model) {
        return if (isBilling) parseBilling(this) else parseSale(this)
    }

    private fun parseSale(model: SaleOrderMultiProfileContainer): SaleOrdersHeaderModel =
        with(model) {

            val current = saleOrdersIndicators.currentData
                ?: SaleOrdersIndicator(campaign = saleOrdersIndicators.currentCampaign)
            val previous = saleOrdersIndicators.previousData
                ?: SaleOrdersIndicator(campaign = saleOrdersIndicators.previousCampaign)

            val goals = ContentModel(
                type = ContentType.SIMPLE_CARD,
                items = buildGoalItems(currencySymbol, current)
            )
            val achievements = ContentModel(
                type = ContentType.SIMPLE_CARD,
                items = buildAchievementsItemsSale(currencySymbol, previous)
            )

            return SaleOrdersHeaderModel(
                titleGoals = textResolver.formatTitleGoalsMultiProfile(current.campaign.takeLastTwo()),
                goals = listOf(goals),
                titleAchievements = textResolver.formatTitleAchievements(previous.campaign.takeLastTwo()),
                achievements = listOf(achievements),
                model = current
            )
        }


    private fun parseBilling(model: SaleOrderMultiProfileContainer): SaleOrdersHeaderModel =
        with(model) {

            val current = saleOrdersIndicators.currentData
                ?: SaleOrdersIndicator(campaign = saleOrdersIndicators.currentCampaign)

            val achievements = ContentModel(
                type = ContentType.SIMPLE_CARD,
                items = buildAchievementsItemsBilling(currencySymbol, current)
            )
            val accomplishments = ContentModel(
                type = ContentType.SIMPLE_CARD,
                items = buildAccomplishmentItems(currencySymbol, current)
            )
            return SaleOrdersHeaderModel(
                titleAchievements = textResolver.formatTitleAchievementProgress(current.campaign.takeLastTwo()),
                achievements = listOf(achievements, accomplishments),
                model = current
            )
        }

    private fun buildGoalItems(
        currencySymbol: String,
        model: SaleOrdersIndicator
    ): List<ContentBaseModel> {
        return listOf(
            createSingleItem(
              textResolver.formatNetSalesTitle(),
                formatCurrency(currencySymbol, model.netSaleGoal)
            ),
            Separator(getColor(R.color.colorDividerMultiProfile)),

            createSingleItem(
                textResolver.formatOrderTitle(),
                model.ordersGoal.toString()
            ),
            Separator(getColor(R.color.colorDividerMultiProfile)),

            createSingleItem(
                textResolver.formatAverageAmountTitle(),
                formatCurrency(currencySymbol, model.averageAmountGoal)
            ),
            Separator(getColor(R.color.colorDividerMultiProfile)),

            createSingleItem(
                textResolver.formatActivesRetentionFinalTitle(),
                model.activesFinals.toString()
            ),
            Separator(getColor(R.color.colorDividerMultiProfile)),

            createSingleItem(
                textResolver.getActivityPercentageTitle(),
                textResolver.formatPercentageStringLabel(model.activityGoal.toPercentageTruncateDecimal())
            )
        )
    }

    private fun buildAchievementsItemsSale(
        currencySymbol: String,
        model: SaleOrdersIndicator
    ): List<ContentBaseModel> {
        val multimarkOrdersHighValueDisclaimerValue = if(model.multibrandOrdersHighValue.isNullOrEmpty()) Constant.STRING_ZERO else model.multibrandOrdersHighValue
        val multimarkOrdersHighValueDisclaimer = Single(
            "",
            textResolver.getMultimarkHighValueOrders(multimarkOrdersHighValueDisclaimerValue))
        return listOf(createComplexSale(currencySymbol, model)) + multimarkOrdersHighValueDisclaimer
    }

    private fun buildAchievementsItemsBilling(
        currencySymbol: String,
        model: SaleOrdersIndicator
    ): List<ContentBaseModel> {
        val multimarkOrdersHighValueDisclaimerValue = if(model.multibrandOrdersHighValue.isNullOrEmpty()) Constant.STRING_ZERO else model.multibrandOrdersHighValue
        val multimarkOrdersHighValueDisclaimer = Single(
            "",
            textResolver.getMultimarkHighValueOrders(multimarkOrdersHighValueDisclaimerValue))

        return listOf(createComplexBilling(currencySymbol, model)) + multimarkOrdersHighValueDisclaimer
    }

    private fun buildAccomplishmentItems(
        currencySymbol: String,
        model: SaleOrdersIndicator
    ): List<ContentBaseModel> {
        return listOf(
            createMultipleActivesItem(model),
            Separator(getColor(R.color.colorDividerMultiProfile)),
            createMultipleActivityItem(model)
        )
    }

    private fun createSingleItem(title: String, description: String): Single {
        return SingleLeft(
            title, description, getColor(R.color.colorRangeLabel), getColor(android.R.color.black)
        )
    }


    private fun createMultipleActivesItem(model: SaleOrdersIndicator): Multiple {
        return Multiple(
            titleLeft = textResolver.formatActivesRetentionTitle(),
            description = textResolver.formatPercentageStringLabel(
                model.activesRetentionPercentage.toPercentageTruncateDecimal()
            ),
            titleRight = textResolver.formatAchievementTitle(),
            complianceProgress = model.fulfillmentRetentionPercentage.toPercentageNumber()
        )
    }

    private fun createMultipleActivityItem(model: SaleOrdersIndicator): Multiple {
        return Multiple(
            titleLeft = textResolver.getActivityTitle().toUpperCase(Locale.getDefault()),
            description = textResolver.formatPercentageStringLabel(
                model.activityPercentage.toPercentageTruncateDecimal()
            ),
            titleRight = textResolver.formatAchievementTitle(),
            complianceProgress = model.fulfillmentActivityPercentage.toPercentageNumber()
        )
    }

    private fun createComplexSale(
        currencySymbol: String,
        model: SaleOrdersIndicator,
    ): Complex {
        return Complex(
            createProgressItem(currencySymbol, model, useOrders = false),
            createProgressItem(currencySymbol, model, useOrders = true),
            createProgressPMNPItem(currencySymbol, model),
            createProgressItem(currencySymbol, model, useOrders = true),
            createRanges(model.ordersRange),
            false
        )
    }

    private fun createComplexBilling(
        currencySymbol: String,
        model: SaleOrdersIndicator,
        ): Complex {
        return Complex(
            createProgressItem(currencySymbol, model, useOrders = false),
            createProgressItem(currencySymbol, model, useOrders = true),
            createProgressPMNPItem(currencySymbol, model),
            createProgressActivityPercentageItem(currencySymbol, model),
            createRanges(model.ordersRange),
            true
        )
    }

    private fun createProgressItem(
        currencySymbol: String,
        model: SaleOrdersIndicator,
        useOrders: Boolean
    ): Progress {
        val goalIndicator = createGoalIndicator(currencySymbol, model, useOrders)
        return Progress(model = goalIndicator)
    }

    private fun createProgressPMNPItem(
        currencySymbol: String,
        model: SaleOrdersIndicator): Progress {
        val goalIndicator = createGoalIndicatorPNMP(currencySymbol, model)
        return Progress(model = goalIndicator)
    }

    private fun createProgressActivityPercentageItem(
        currencySymbol: String,
        model: SaleOrdersIndicator): Progress {
        val goalIndicator = createGoalIndicatorActivityPercentage(model)
        return Progress(model = goalIndicator)
    }

    private fun createGoalIndicator(
        currencySymbol: String,
        model: SaleOrdersIndicator,
        useOrders: Boolean = false
    ) = IndicatorGoalBar.Model(
        currentTarget = if (useOrders) formatDescriptionOrders(model.orders) else formatCurrency(
            currencySymbol,
            model.netSale
        ),
        currentProgress = if (useOrders) model.orders else model.netSale.toInt(),
        maxProgress = if (useOrders) model.ordersGoal else model.netSaleGoal.toInt(),
        enableAnimation = false,
        goalDescription = if (useOrders) formatGoalOrders(model.ordersGoal) else formatGoalSale(
            currencySymbol, model.netSaleGoal
        ),
        goalMessage = formatFulfillment(
            if (useOrders) model.fulfillmentOrderPercentage
            else model.fulfillmentNetSalePercentage
        ),
        goalMessageColor = getColor(android.R.color.black),
        type = getType(model, useOrders)
    )

    private fun createGoalIndicatorPNMP(
        currencySymbol: String,
        model: SaleOrdersIndicator) = IndicatorGoalBar.Model(
        currentTarget = formatCurrency(currencySymbol, model.averageAmount) + " PMNP",
        currentProgress = model.averageAmount.toInt(),
        maxProgress =  model.averageAmountGoal.toInt(),
        enableAnimation = false,
        goalDescription = "Meta: " + formatCurrency(currencySymbol, model.averageAmountGoal),
        goalMessage = formatFulfillment(model.fulfillmentOrderAveragePercentage),
        goalMessageColor = getColor(android.R.color.black),
        type = getTypePMNP(model)
    )

    private fun createGoalIndicatorActivityPercentage(
        model: SaleOrdersIndicator) = IndicatorGoalBar.Model(
        currentTarget = (model.activityPercentage*100).truncateLowerOneDecimal().toString() + " % Actividad"  ,
        currentProgress = (model.activityPercentage*100).truncateLowerOneDecimal().toInt(),
        maxProgress =  (model.activityGoal*100).truncateLowerOneDecimal().toInt(),
        enableAnimation = false,
        goalDescription = "Meta: " + (model.activityGoal*100).truncateLowerOneDecimal() + "%" ,
        goalMessage = formatFulfillment(model.fulfillmentActivityPercentage),
        goalMessageColor = getColor(android.R.color.black),
        type = getTypeActivity(model)
    )


    private fun createRanges(ranges: List<SaleOrdersOrderRange>): Range {
        val title = textResolver.getOrderDetailTitle()
        val items = ranges.filter{ LOW_VALUE_PLUS != it.range }.sortedBy { it.position }.map {
            DefaultGridModel(
                label = it.range.removeFirstWord(),
                value = it.amount.formatWithThousands()
            )
        }
        return Range(title, items)
    }

    private fun formatGoalSale(currencySymbol: String, goal: Double): String {
        return textResolver.formatNetSaleGoalMultiProfile(
            currencySymbol,
            goal.formatWithLowerThousands()
        ).replace(NEW_LINE, BLANK_SPACE)
    }

    private fun formatGoalOrders(goal: Int): String {
        return textResolver.formatOrdersGoalDescription(goal.toString())
    }

    private fun formatFulfillment(percentage: Double): String {
        return textResolver.formatFulfillmentProgress(percentage.toPercentageNumber().toString())
    }

    private fun formatPMNPMessage(goal: Double): String {
        return textResolver.formatPMNPProgress(goal.toString())
    }

    private fun formatActivityPercentageProgress(goal: Double): String {
        return textResolver.formatActivityPercentageProgress(goal.toPercentageNumber().toString())
    }


    private fun formatPercentage(value: Double) =
        textResolver.formatPercentageStringLabel(value.toPercentageTruncateDecimal())

    private fun formatCurrency(currency: String, value: Double) =
        textResolver.formatCurrency(currency, value.formatWithLowerThousands())

    private fun formatDescriptionOrders(orders: Int) = textResolver.formatQuantityOrders(orders)

    private fun getType(model: SaleOrdersIndicator, useOrders: Boolean): IndicatorGoalBar.GoalType =
        with(model) {
            val remaining = if (useOrders) pendingOrderGoal else pendingNetSaleGoal
            return when {
                remaining.isNegative() || remaining.isZero() -> IndicatorGoalBar.GoalType.SUCCESS
                else -> IndicatorGoalBar.GoalType.NORMAL
            }
        }

    private fun getTypePMNP(model: SaleOrdersIndicator): IndicatorGoalBar.GoalType =
        with(model) {
            val remaining = pendingPMNP
            return when {
                remaining.isNegative() || remaining.isZero() -> IndicatorGoalBar.GoalType.SUCCESS
                else -> IndicatorGoalBar.GoalType.NORMAL
            }
        }

    private fun getTypeActivity(model: SaleOrdersIndicator): IndicatorGoalBar.GoalType =
        with(model) {
            val remaining = pendingActivity.toPercentageNumber()
            return when {
                remaining >= 100  -> IndicatorGoalBar.GoalType.SUCCESS
                else -> IndicatorGoalBar.GoalType.NORMAL
            }
        }

    private fun getColor(color: Int) = ContextCompat.getColor(textResolver.context, color)

}
