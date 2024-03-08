package biz.belcorp.salesforce.core.features.utils

import android.content.Context
import androidx.annotation.StringRes
import biz.belcorp.salesforce.core.R
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*


open class CoreTextResolver(val context: Context) : BaseTextResolver() {

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
        return context.getString(resId, *formatArgs)
    }

    fun getTextUaCountry(vararg args: Any?): String {
        val format = context.getString(R.string.text_your_country)
        return stringFormat(format, *args)
    }

    fun getTextUaRegion(vararg args: Any?): String {
        val format = context.getString(R.string.text_your_region)
        return stringFormat(format, *args)
    }

    fun getTextUaZone(vararg args: Any?): String {
        val format = context.getString(R.string.text_your_zone)
        return stringFormat(format, *args)
    }

    fun getUaLabelByRole(role: Rol): String {
        return when {
            role.isDV() -> getTextUaCountry()
            role.isGR() -> getTextUaRegion()
            role.isGZ() -> getTextUaZone()
            else -> EMPTY_STRING
        }
    }

    fun formatUaChildText(vararg args: Any?, role: Rol): String = with(role) {
        val resId = when {
            isGR() -> R.string.text_region_with_ua
            isGZ() -> R.string.text_zone_with_ua
            isSE() -> R.string.text_section_with_ua
            else -> R.string.text_region_with_ua
        }
        return context.getString(resId, *args)
    }

    fun getCampaign(vararg args: Any?): String {
        val resId = context.getString(R.string.text_campaign)
        return stringFormat(resId, *args)
    }

    fun getCampaignFormatted(campaignCode: String): String {
        val campaignFormatted =
            if (campaignCode.isNotEmpty()) campaignCode.takeLastTwo() else EMPTY_STRING
        return context.getString(R.string.text_campaign, campaignFormatted)
    }
}
