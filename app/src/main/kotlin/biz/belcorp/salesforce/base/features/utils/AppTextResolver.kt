package biz.belcorp.salesforce.base.features.utils

import android.content.Context
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.core.base.BaseTextResolver

class AppTextResolver(val context: Context) : BaseTextResolver() {

    fun formatToolbarSaleText(vararg args: Any?): String {
        val format = context.getString(R.string.text_toolbar_period_sale)
        return stringFormat(format, *args)
    }

    fun formatToolbarBillingText(vararg args: Any?): String {
        val format = context.getString(R.string.text_toolbar_period_billing)
        return stringFormat(format, *args)
    }

    fun formatUserDescriptionMultiProfile(vararg args: Any?): String {
        val format = context.getString(R.string.text_userinfo_description_multiprofile)
        return stringFormat(format, *args)
    }

    fun formatUserDescriptionSE(vararg args: Any?): String {
        val format = context.getString(R.string.text_userinfo_description_se)
        return stringFormat(format, *args)
    }

    fun formatUserLevel(vararg args: Any?): String {
        val format = context.getString(R.string.text_userinfo_level)
        return stringFormat(format, *args)
    }

    fun formatBannerBillingDescription(vararg args: Any?): String {
        val format = context.getString(R.string.billing_banner_description)
        return stringFormat(format, *args)
    }
}
