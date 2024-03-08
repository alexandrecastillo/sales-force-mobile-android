package biz.belcorp.salesforce.modules.digital.features.utils

import android.content.Context
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.utils.CoreTextResolver
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.core.utils.isGZ
import biz.belcorp.salesforce.core.utils.takeLastTwo
import biz.belcorp.salesforce.modules.digital.R
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType
import java.util.*

class DigitalTextResolver(context: Context) : CoreTextResolver(context) {

    fun getGridHeaderText(
        @DigitalFilterType type: Int
    ): String {
        return when (type) {
            DigitalFilterType.SUBSCRIBED -> context.getString(R.string.digital_header_grid_subscribed)
            DigitalFilterType.SHARE -> context.getString(R.string.digital_header_grid_share)
            else -> context.getString(R.string.digital_header_grid_buy)
        }
    }

    fun getGridPercentageHeaderText(
        @DigitalFilterType type: Int
    ): String {
        return when (type) {
            DigitalFilterType.SUBSCRIBED -> context.getString(R.string.digital_header_grid_subscribed_percentage)
            DigitalFilterType.SHARE -> context.getString(R.string.digital_header_grid_share_percentage)
            else -> context.getString(R.string.digital_header_grid_buy_percentage)
        }
    }

    fun getChildInformationText(
        uaName: String,
        storeTitle: String,
        @DigitalFilterType type: Int
    ): String {
        val format = when (type) {
            DigitalFilterType.SUBSCRIBED -> context.getString(R.string.digital_child_information_subscribed)
            DigitalFilterType.SHARE -> context.getString(R.string.digital_child_information_share)
            else -> context.getString(R.string.digital_child_information_buy)
        }
        return stringFormat(format, uaName, storeTitle)
    }

    fun getHeaderTitleText(
        storeTitle: String,
        @DigitalFilterType type: Int
    ): String {
        val format = when (type) {
            DigitalFilterType.SUBSCRIBED -> context.getString(R.string.digital_header_title_subscribed)
            DigitalFilterType.SHARE -> context.getString(R.string.digital_header_title_share)
            else -> context.getString(R.string.digital_header_title_buy)
        }
        return stringFormat(format, storeTitle)
    }

    fun getHeaderPercentageText(value: Int): String {
        val format = context.getString(R.string.digital_header_percentage_value)
        return stringFormat(format, value)
    }

    fun getHeaderProgressText(value1: Int, value2: Int): String {
        val format = context.getString(R.string.digital_header_progress_value)
        return stringFormat(format, value1, value2)
    }

    fun getHeaderDescription1Text(
        storeTitle: String,
        @DigitalFilterType type: Int
    ): String {
        val format = when (type) {
            DigitalFilterType.SUBSCRIBED -> context.getString(R.string.digital_header_description_1_subscribed)
            DigitalFilterType.SHARE -> context.getString(R.string.digital_header_description_1_share)
            else -> context.getString(R.string.digital_header_description_1_buy)
        }
        return stringFormat(format, storeTitle)
    }

    fun getHeaderDescription2Text(
        storeTitle: String,
        campaign: String,
        @DigitalFilterType type: Int
    ): String {
        val format = when (type) {
            DigitalFilterType.SUBSCRIBED -> context.getString(R.string.digital_header_description_2_subscribed)
            DigitalFilterType.SHARE -> context.getString(R.string.digital_header_description_2_share)
            else -> context.getString(R.string.digital_header_description_2_buy)
        }
        return stringFormat(format, storeTitle, campaign.takeLastTwo())
    }

    fun getLowerUaRolText(role: Rol): String = with(role) {
        return when {
            isDV() -> context.getString(R.string.column_header_dv).toUpperCase(Locale.getDefault())
            isGR() -> context.getString(R.string.column_header_gr).toUpperCase(Locale.getDefault())
            isGZ() -> context.getString(R.string.column_header_gz).toUpperCase(Locale.getDefault())
            else -> Constant.EMPTY_STRING
        }
    }

    fun getSubtitleDetailText(role: Rol, campaign: String): String = with(role) {
        val format = when {
            isDV() -> context.getString(R.string.digital_subtitle_detail_dv)
            isGR() -> context.getString(R.string.digital_subtitle_detail_gr)
            isGZ() -> context.getString(R.string.digital_subtitle_detail_gz)
            else -> null
        }
        val subtitleSe = context.getString(R.string.digital_subtitle_detail_se)
        return format?.let { stringFormat(it, campaign) } ?: subtitleSe
    }

}
