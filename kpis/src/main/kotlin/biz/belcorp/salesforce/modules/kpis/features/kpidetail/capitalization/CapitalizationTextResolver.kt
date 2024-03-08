package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.modules.kpis.R as KpisR

class CapitalizationTextResolver(val context: Context) : BaseTextResolver() {

    fun formatPercentage(vararg args: Any): String {
        val format = context.getString(KpisR.string.percentage_string_format)
        return stringFormat(format, *args)
    }

    fun formatYourResultsInCampaign(isParent: Boolean, vararg args: Any): String {
        val intRes = if (isParent) KpisR.string.text_kpi_your_results_title_formatted
        else KpisR.string.text_kpi_results_title_formatted

        val format = context.getString(intRes)
        return stringFormat(format, *args)
    }

    fun getPossibleEntries() = context.getString(KpisR.string.text_possible_entries)

    fun getPossiblesReentries() = context.getString(KpisR.string.text_possible_re_entries)

    fun getIncomesTitle() = context.getString(KpisR.string.incomes)

    fun getReentriesTitle() = context.getString(KpisR.string.reentries)

    fun getAdjustedIncomesTitle() = context.getString(KpisR.string.adjusted_incomes)

    fun getExpensesTitle() = context.getString(KpisR.string.expenses)

    fun getCapitalizationTitle() = context.getString(KpisR.string.capitalization)

    fun getRetentionPEGsLabel() = context.getString(KpisR.string.retention_pegs_percentage)

    fun getTotalPotentialsQuantityLabel(quantity: Int) = context.resources
        .getQuantityString(
            KpisR.plurals.text_possible_consultant,
            quantity,
            quantity
        )

    fun getPEGsRetainedText() = context.getString(KpisR.string.retained_pegs).capitalize()

    fun getPEGSRetentionRemainingText() = context
        .getString(KpisR.string.pegtoretain)
        .capitalize()

    fun getCapitalizationProjected() = context.getString(KpisR.string.text_projected_capitalization)

    fun getCapitalizationSectionProjected() = context.getString(KpisR.string.text_projected_section_capitalization)

    fun getToolTipText(isParent: Boolean): String {
        val intRes = if (isParent) KpisR.string.text_your_capitalization_tip
        else KpisR.string.text_capitalization_tip
        return context.getString(intRes)
    }

}


