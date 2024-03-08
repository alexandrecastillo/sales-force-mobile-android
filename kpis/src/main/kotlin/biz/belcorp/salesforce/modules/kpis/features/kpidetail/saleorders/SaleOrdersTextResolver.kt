package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.core.utils.formatWithThousands
import biz.belcorp.salesforce.modules.kpis.R

class SaleOrdersTextResolver(val context: Context) : BaseTextResolver() {

    fun formatSaleOrdersHeader() = context.getString(R.string.sale_orders_header)

    fun formatTitle(vararg args: Any?, isThirdPerson: Boolean = false): String {
        val resource = if (!isThirdPerson) R.string.sale_orders_campaign_title
        else R.string.sale_orders_campaign_title_third
        return stringFormat(context.getString(resource), *args)
    }

    fun formatDescriptionOrders(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_description_orders)
        return stringFormat(format, *args)
    }

    fun formatDefaultTitleBilling(isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.sale_orders_default_title_billing
        else R.string.sale_orders_default_title_billing_third
        return context.getString(resource)
    }

    fun formatSuccessTitleBilling(isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.sale_orders_success_title_billing
        else R.string.sale_orders_success_title_billing_third
        return context.getString(resource)
    }

    fun formatSuccessTitleMultiProfileBilling(isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.sale_orders_success_title_billing_multi_profile
        else R.string.sale_orders_success_title_billing_multi_profile_third
        return context.getString(resource)
    }

    fun formatReachedTitleBilling(isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.sale_orders_reached_title_billing
        else R.string.sale_orders_reached_title_billing_third
        return context.getString(resource)
    }

    fun formatReachedTitleMultiProfileBilling(isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.sale_orders_reached_title_billing_multi_profile
        else R.string.sale_orders_reached_title_billing_multi_profile_third
        return context.getString(resource)
    }

    fun getCatalogSaleTitle() =
        context.getString(R.string.sale_orders_subtitle_billing_catalog_sale)

    fun formatQuantityOrders(amount: Int): String {
        return context.resources.getQuantityString(
            R.plurals.sale_orders_description_orders,
            amount,
            amount.formatWithThousands()
        )
    }

    fun formatCurrencyAmount(vararg args: Any?): String {
        val format = context.getString(R.string.all_number_with_currency)
        return stringFormat(format, *args)
    }

    fun formatDescriptionCatalogSale(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_description_catalog_sale)
        return stringFormat(format, *args)
    }

    fun getOrdersTitle() = context.getString(R.string.sale_orders_orders_title)

    fun formatDescriptionOrdersBilled(amount: Int) =
        context.resources.getQuantityString(
            R.plurals.sale_orders_description_orders_billed,
            amount,
            amount.formatWithThousands()
        )

    fun formatNetSaleDescriptionMultiProfile(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_description_catalog_sale_multi_profile)
        return stringFormat(format, *args)
    }

    fun formatOrdersDescriptionMultiProfile(amount: Int, emoji: String) =
        context.resources.getQuantityString(
            R.plurals.sale_orders_description_orders_multi_profile,
            amount,
            amount, emoji
        )

    fun formatSaleOrdersTitleBilling(vararg args: Any?, isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.sale_orders_title_billing_multi_profile
        else R.string.sale_orders_title_billing_multi_profile_third
        return stringFormat(context.getString(resource), args)
    }

    fun formatSaleDescription(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_amount_sale_description)
        return stringFormat(format, *args)
    }

    fun formatFulfillmentProgress(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_fulfillment_progress)
        return stringFormat(format, *args)
    }

    fun formatPMNPProgress(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_pmnp_message_goal)
        return stringFormat(format, *args)
    }

    fun formatActivityPercentageProgress(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_activity_percentage_message_goal)
        return stringFormat(format, *args)
    }




    fun formatAchievementTitle() = context.getString(R.string.achievement_title)

    fun formatAverageAmountTitle() = context.getString(R.string.sale_orders_title_pmnp)

    fun formatNetSalesTitle() = context.getString(R.string.sale_orders_title_net_sales)

    fun formatOrderTitle() = context.getString(R.string.sale_orders_title_orders)

    fun formatActivesRetentionTitle() =
        context.getString(R.string.sale_orders_title_actives_retention)

    fun formatActivesRetentionFinalTitle() =
        context.getString(R.string.sale_orders_title_actives_final)

    fun getOrdersAverageTitle() = context.getString(R.string.order_average)

    fun getActivityTitle() = context.getString(R.string.activity)

    fun getActivityPercentageTitle() = context.getString(R.string.activity_percentage)


    fun getFinalActivesTitle() = context.getString(R.string.final_actives_label)

    fun getPegsTitle() = context.getString(R.string.pegs)

    fun getActiveRetentionTitle() = context.getString(R.string.active_retention)

    fun getFinalActiveRetentionTitle(year: String) =
        context.getString(R.string.retention_final_actives, year)

    fun getOrderDetailTitle() = context.getString(R.string.orders_detail)

    fun getTitleBilling(campaign: String, isThirdPerson: Boolean = false): String {
        val resource = if (!isThirdPerson) R.string.title_sales_order_billing
        else R.string.title_sales_order_billing_third
        return context.getString(resource, campaign)
    }

    fun getTitleSale(campaign: String, isThirdPerson: Boolean = false): String {
        val resource = if (!isThirdPerson) R.string.title_sales_order_sale
        else R.string.title_sales_order_sale_third
        return context.getString(resource, campaign)
    }

    fun formatPercentageStringLabel(vararg args: Any?): String {
        val format = context.getString(R.string.percentage_string_format)
        return stringFormat(format, *args)
    }

    fun formatHomeSaleOrderSaleTitle(vararg args: Any?, isThirdPerson: Boolean = false): String {
        val format =
            if (isThirdPerson) context.getString(R.string.home_saleOrdersSaleTitleThirdPerson)
            else context.getString(R.string.home_saleOrdersSaleTitle)
        return stringFormat(format, *args)
    }

    fun formatHomeSaleOrderCatalogSale(vararg args: Any?): String {
        val format = context.getString(R.string.home_saleOrdersCatalogSale)
        return stringFormat(format, *args)
    }

    fun formatOrdersGoalDescription(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_goal_description)
        return stringFormat(format, *args)
    }

    fun formatCatalogSaleDescription(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_catalog_sale_description)
        return stringFormat(format, *args)
    }

    fun formatNetSaleGoalMultiProfile(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_catalog_sale_description_multi_profile)
        return stringFormat(format, *args)
    }

    fun formatCurrency(vararg args: Any?): String {
        val format = context.getString(R.string.number_with_currency)
        return stringFormat(format, *args)
    }

    fun formatTitleGoalsMultiProfile(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_title_goals)
        return stringFormat(format, *args)
    }

    fun formatTitleAchievements(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_title_achievements)
        return stringFormat(format, *args)
    }

    fun formatTitleAchievementProgress(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_title_achievements_progress)
        return stringFormat(format, *args)
    }

    fun formatSaleOrdersSaleDescriptionGZ(vararg args: Any?): String {
        val format = context.getString(R.string.home_saleOrdersSaleDescriptionGZ)
        return stringFormat(format, *args)
    }

    fun getMultimarkHighValueOrders(multimarkHighValueOrders: String) =
        context.getString(R.string.sale_orders_disclaimer_multimark_high_value_orders, multimarkHighValueOrders)

    fun getPMNPProgressTitle(pmnpValue: String) =
        context.getString(R.string.progress_pmnp_title, pmnpValue)

}
