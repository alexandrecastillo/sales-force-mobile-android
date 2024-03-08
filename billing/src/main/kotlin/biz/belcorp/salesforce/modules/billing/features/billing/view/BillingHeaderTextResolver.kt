package biz.belcorp.salesforce.modules.billing.features.billing.view

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.modules.billing.R

class BillingHeaderTextResolver(private val context: Context) : BaseTextResolver() {

    fun getSaleDescription(vararg args: Any?, isThirdPerson: Boolean = false): String {
        val resource =
            if (!isThirdPerson) R.string.advance_sale_description
            else R.string.advance_sale_description_third
        return stringFormat(context.getString(resource), *args)
    }

    fun getSaleBrightDescription(vararg args: Any?, isThirdPerson: Boolean = false): String {
        val resource = if (!isThirdPerson) R.string.advance_bright_sale_description
        else R.string.advance_bright_sale_description_third
        return stringFormat(context.getString(resource), *args)
    }

    fun getSaleDescriptionMultiProfile(vararg args: Any?, isThirdPerson: Boolean = false): String {
        return getSaleBrightDescription(*args, isThirdPerson = isThirdPerson)
    }

    fun getSaleDescriptionSuccessMultiProfile(vararg args: Any?, isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.advance_sale_success_description_multiprofile
        else R.string.advance_sale_success_description_multiprofile_third
        return stringFormat(context.getString(resource), *args)
    }

    fun getSaleSuccessBrightDescription(
        vararg args: Any?,
        isThirdPerson: Boolean = false
    ): String {
        val resource = if (!isThirdPerson) R.string.advance_bright_sale_success_description
        else R.string.advance_bright_sale_success_description_third
        return stringFormat(context.getString(resource), *args)
    }

    fun getSaleReachedBrightDescription(
        vararg args: Any?,
        isThirdPerson: Boolean = false
    ):String {
        val resource = if (!isThirdPerson) R.string.advance_bright_sale_reached_description
        else R.string.advance_bright_sale_reached_description_third
        return stringFormat(context.getString(resource), *args)
    }

    fun getOrdersDescription(
        vararg args: Any?,
        quantity: Int,
        isThirdPerson: Boolean = false
    ): String {
        val resource = if (!isThirdPerson) R.plurals.advance_orders_description
        else R.plurals.advance_orders_description_third
        return context.resources.getQuantityString(resource, quantity, *args)
    }

    fun getOrdersBrightDescription(
        vararg args: Any?,
        quantity: Int,
        isThirdPerson: Boolean
    ): String {
        val resource = if (!isThirdPerson) R.plurals.advance_bright_orders_description
        else R.plurals.advance_bright_orders_description_third
        return context.resources.getQuantityString(resource, quantity, *args)
    }

    fun getOrdersSuccessDescription(
        vararg args: Any?,
        quantity: Int,
        isThirdPerson: Boolean = false
    ): String {
        val resource = if (!isThirdPerson) R.plurals.advance_orders_description_success
        else R.plurals.advance_orders_description_success_third
        return context.resources.getQuantityString(resource, quantity, *args)
    }

    fun getOrdersSuccessDescriptionMultiProfile(
        vararg args: Any?,
        quantity: Int,
        isThirdPerson: Boolean = false
    ): String {
        val resource = if (!isThirdPerson) R.plurals.advance_orders_description_success_multiprofile
        else R.plurals.advance_orders_description_success_multiprofile_third
        return context.resources.getQuantityString(resource, quantity, *args)
    }

    fun getOrdersReachedDescription(
        vararg args: Any?,
        quantity: Int,
        isThirdPerson: Boolean = false
    ): String {
        val resource = if (!isThirdPerson) R.plurals.advance_orders_description_reached
        else R.plurals.advance_orders_description_reached_third
        return context.resources.getQuantityString(resource, quantity, *args)
    }

    fun getAmountWithCurrency(vararg args: Any?): String {
        val format = context.getString(R.string.text_amount_with_currency)
        return stringFormat(format, *args)
    }

    fun getTitleSaleBilling() = context.getString(R.string.billing_sale_title)

    fun getTitleOrdersBilling() = context.getString(R.string.billing_orders_title)

}
