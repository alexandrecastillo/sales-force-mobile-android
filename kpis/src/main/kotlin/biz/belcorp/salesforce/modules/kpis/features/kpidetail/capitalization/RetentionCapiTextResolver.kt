package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.core.utils.formatWithThousands
import biz.belcorp.salesforce.modules.kpis.R

class RetentionCapiTextResolver(val context: Context) : BaseTextResolver() {

    fun formatHeader(): String {
        return context.getString(R.string.retention_capi_header)
    }

    fun formatTitle(vararg args: Any?, isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.retention_capi_campaign_title
        else R.string.retention_capi_campaign_title_third
        return stringFormat(context.getString(resource), *args)
    }

    fun formatSubTitleEntries() = context.getString(R.string.retention_capi_subtitle_entries)

    fun formatDescriptionEntries(quantity: Int): String {
        return context.resources.getQuantityString(
            R.plurals.retention_capi_description_entries,
            quantity,
            quantity.formatWithThousands()
        )
    }

    fun formatSubTitleRetention() = context.getString(R.string.retention_capi_subtitle_retention)

    fun formatDescriptionRetention(quantity: Int, emoji: String): String {
        return context.resources.getQuantityString(
            R.plurals.retention_capi_description_retention,
            quantity,
            quantity.formatWithThousands(),
            emoji
        )
    }

    fun formatSubTitleEntriesBilling(isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.retention_capi_subtitle_entries_billing
        else R.string.retention_capi_subtitle_entries_billing_third
        return context.getString(resource)
    }

    fun formatCapitalizationProjected(vararg args: Any?): String {
        val format = context.getString(R.string.retention_capi_description_porjected_capi)
        return stringFormat(format, *args)
    }

    fun formatDescription(vararg args: Any?): String {
        val format = context.getString(R.string.retention_capi_description)
        return stringFormat(format, *args)
    }

    fun formatSubTitleBilling(isThirdPerson: Boolean): String {
        val resource = if (!isThirdPerson) R.string.retention_capi_subtitle_retention_billing
        else R.string.retention_capi_subtitle_retention_billing_third
        return context.getString(resource)
    }

    fun formatDescriptionRetentionBilling(vararg args: Any?): String {
        val format = context.getString(R.string.retention_capi_description_retention_billing)
        return stringFormat(format, *args)
    }

    fun formatDescriptionBillingMultiProfile(isThirdPerson: Boolean): String {
        val resource =
            if (!isThirdPerson) R.string.retention_capi_subtitle_retention_billing_multi_profile
            else R.string.retention_capi_subtitle_retention_billing_multi_profile_third
        return context.getString(resource)
    }
}
