package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.EmojiCode
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.kpis.R
import java.util.*

class NewCycleTextResolver(val context: Context) : BaseTextResolver() {

    fun formatNewCycleHeader() = context.getString(R.string.new_cycle_header)

    fun formatSaleTitle(vararg args: Any?, isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.new_cycle_campaign_title
        else R.string.new_cycle_campaign_title_third
        return stringFormat(context.getString(resource), *args)
    }

    fun formatBillingTitle(isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.new_cycle_billing_title
        else R.string.new_cycle_billing_title_third
        return context.getString(resource)
    }

    fun formatConsultants(vararg args: Any?, quantity: Int): String {
        return context.resources.getQuantityString(R.plurals.new_cycle_consultants, quantity, *args)
    }

    fun formatConsultantsBilling(vararg args: Any?, quantity: Int): String {
        return context.resources.getQuantityString(
            R.plurals.new_cycle_consultants_billing,
            quantity,
            *args
        )
    }

    fun formatConsultantsHighValue(vararg args: Any?, quantity: Int): String {
        return context.resources.getQuantityString(
            R.plurals.new_cycle_consultants_high_value,
            quantity,
            *args
        )
    }

    fun formatConsultantsHighValueBilling(vararg args: Any?, quantity: Int): String {
        return context.resources.getQuantityString(
            R.plurals.new_cycle_consultants_high_value_billing,
            quantity,
            *args
        )
    }

    fun formatSegmentText(vararg args: Any?): String {
        val format = context.getString(R.string.new_cycle_in_delimiter)
        return stringFormat(format, *args)
    }

    fun getTitle6d6(): String {
        return context.getString(R.string.title_newcycles_6d6)
    }

    fun getTitle5d5(): String {
        return context.getString(R.string.title_newcycles_5d5)
    }

    fun getTitle4d4(): String {
        return context.getString(R.string.title_newcycles_4d4)
    }

    fun getTitle3d3(): String {
        return context.getString(R.string.title_newcycles_3d3)
    }

    fun getTitle2d2(): String {
        return context.getString(R.string.title_newcycles_2d2)
    }

    fun getHighValueTitle6d6(): String {
        return context.getString(R.string.title_high_value_newcycles_6d6)
    }

    fun getHighValueTitle5d5(): String {
        return context.getString(R.string.title_high_value_newcycles_5d5)
    }

    fun getHighValueTitle4d4(): String {
        return context.getString(R.string.title_high_value_newcycles_4d4)
    }

    fun getLowValueRetainedTitle(): String {
        return context.getString(R.string.title_low_value_retained)
    }

    fun getLowValueRetentionTitle(): String {
        return context.getString(R.string.title_low_value_retention)
    }

    fun getHighValueRetainedTitle(): String {
        return context.getString(R.string.title_high_value_retained)
    }

    fun formatPercentageStringLabel(vararg args: Any?): String {
        val format = context.getString(R.string.percentage_string_format)
        return stringFormat(format, *args)
    }

    fun getHighValueRetentionTitle(role: Rol, isMultiProfile: Boolean): String {
        return if (role.isSE()) {
            context.getString(R.string.title_high_value_retention)
        } else {
            context.getString(R.string.title_high_value_retention_multiprofile)
                .toUpperCase(isMultiProfile)
        }
    }

    fun getFirstColumnTitle(rol: Rol): String {
        return when {
            rol.isDV() -> context.getString(R.string.column_header_dv)
                .toUpperCase(Locale.getDefault())
            rol.isGR() -> context.getString(R.string.column_header_gr)
                .toUpperCase(Locale.getDefault())
            rol.isGZ() -> context.getString(R.string.column_header_gz)
                .toUpperCase(Locale.getDefault())
            else -> EMPTY_STRING
        }
    }

    fun getSaleTitleForNewCycle(role: Rol, campaign: String, isThirdPerson: Boolean): String {
        return when {
            role.isSE() -> context.getString(
                R.string.new_cycle_in_x_campaign_message_se_sale,
                campaign
            )
            role.isMultiProfile() -> getSaleGzTitle(isThirdPerson, campaign)
            else -> context.getString(R.string.new_cycle_in_x_campaign_message_se_sale, campaign)
        }
    }

    private fun getSaleGzTitle(isThirdPerson: Boolean, campaign: String): String {
        val resId =
            if (isThirdPerson) R.string.text_new_cycle_sale_gz_third_person_title else R.string.new_cycle_in_x_campaign_message_se_sale
        return context.getString(resId, campaign)
    }

    fun getBillingTitleForNewCycle(campaign: String): String {
        return context.getString(
            R.string.new_cycle_in_x_campaign_message_se_billing,
            campaign
        )
    }

    fun getRetentionResultTitle(role: Rol, campaign: String, isThirdPerson: Boolean): String {
        return when {
            role.isMultiProfile() -> getMultiProfileRetentionTitle(campaign, isThirdPerson)
            role.isSE() -> context.getString(
                R.string.text_kpi_your_results_title,
                campaign
            )
            else -> context.getString(R.string.text_kpi_your_results_title, campaign)
        }
    }

    private fun getMultiProfileRetentionTitle(campaign: String, isThirdPerson: Boolean): String {
        val resId =
            if (isThirdPerson) R.string.text_new_cycle_results_third_person_title else R.string.text_kpi_your_results_title_gz
        return context.getString(resId, campaign)
    }

    fun getLowValueTitle(currentConsultants: String?): String {
        return context.getString(
            R.string.new_cycle_consultants_low_value_segment,
            createEmoji(EmojiCode.WOMAN),
            currentConsultants
        )
    }

    fun getHighValueTitle(currentConsultants: String?): String {
        return context.getString(
            R.string.new_cycle_consultants_high_value_segment,
            createEmoji(EmojiCode.WOMAN),
            currentConsultants
        )
    }

    fun getReward(lowValueBonus: String, highValueBonus: String, isThirdPerson: Boolean): String {
        val resId =
            if (isThirdPerson) R.string.text_newcycle_tooltip_third_person else R.string.text_newcycle_tooltip
        return context.getString(resId, lowValueBonus, highValueBonus)
    }

    fun formatCurrency(vararg args: Any?): String {
        val format = context.getString(R.string.number_with_currency)
        return stringFormat(format, *args)
    }

}
