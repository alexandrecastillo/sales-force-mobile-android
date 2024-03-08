package biz.belcorp.salesforce.modules.consultants.features.filters.utils

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.utils.takeLastTwo
import biz.belcorp.salesforce.modules.consultants.R

class ConsultantsTextResolver(private val context: Context) : BaseTextResolver() {

    fun parseNewConstancy(vararg args: Any?): String {
        val format = context.getString(R.string.consultants_new)
        return stringFormat(format, *args)
    }

    fun getNewInconstant() = stringFormat(context.getString(R.string.consultant_new_inconstant))

    fun parseAmountWithCurrency(vararg args: Any?): String {
        val format = context.getString(R.string.consultant_amount_with_currency)
        return stringFormat(format, *args)
    }

    fun getSubscribedText(storeTitle: String): String {
        val format = context.getString(R.string.digital_indicator_subscribed)
        return stringFormat(format, storeTitle)
    }

    fun getNoSubscribedText(storeTitle: String): String {
        val format = context.getString(R.string.digital_indicator_no_subscribed)
        return stringFormat(format, storeTitle)
    }

    fun getShareText(storeTitle: String): String {
        val format = context.getString(R.string.digital_indicator_share)
        return stringFormat(format, storeTitle)
    }

    fun getNoShareText(storeTitle: String): String {
        val format = context.getString(R.string.digital_indicator_no_share)
        return stringFormat(format, storeTitle)
    }

    fun getBuyText(storeTitle: String): String {
        val format = context.getString(R.string.digital_indicator_buy)
        return stringFormat(format, storeTitle)
    }

    fun getNoBuyText(storeTitle: String): String {
        val format = context.getString(R.string.digital_indicator_no_buy)
        return stringFormat(format, storeTitle)
    }

}
