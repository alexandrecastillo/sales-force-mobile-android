package biz.belcorp.salesforce.modules.digital.features.digital.view.detail.shared

import android.content.Context
import androidx.annotation.StringRes
import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.salesforce.core.constants.FilterKey
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.modules.digital.R
import biz.belcorp.salesforce.modules.digital.features.constants.DigitalFilterType

object DetailSelectorBuilder {

    private var context: Context? = null
    private var filterType: Int = KpiType.NONE

    fun init(context: Context): DetailSelectorBuilder {
        DetailSelectorBuilder.context = context
        return this
    }

    fun with(@DigitalFilterType kpiType: Int): DetailSelectorBuilder {
        filterType = kpiType
        return this
    }

    fun build(): List<SelectorModel> {
        return when (filterType) {
            DigitalFilterType.SUBSCRIBED -> createSubscribedFilters()
            DigitalFilterType.BUY -> createBuyFilters()
            DigitalFilterType.SHARE -> createShareFilters()
            else -> emptyList()
        }
    }

    private fun createSubscribedFilters() = listOf(
        createSelector(FilterKey.KEY_ALL, R.string.digital_filter_all, selected = true),
        createSelector(FilterKey.KEY_DIGITAL_SUBSCRIBED, R.string.digital_filter_subscribed),
        createSelector(FilterKey.KEY_DIGITAL_NO_SUBSCRIBED, R.string.digital_filter_no_subscribed)
    )

    private fun createBuyFilters() = listOf(
        createSelector(FilterKey.KEY_DIGITAL_SUBSCRIBED, R.string.digital_filter_all, selected = true),
        createSelector(FilterKey.KEY_DIGITAL_BUY, R.string.digital_filter_buy),
        createSelector(FilterKey.KEY_DIGITAL_NO_BUY, R.string.digital_filter_no_buy)
    )

    private fun createShareFilters() = listOf(
        createSelector(FilterKey.KEY_DIGITAL_SUBSCRIBED, R.string.digital_filter_all, selected = true),
        createSelector(FilterKey.KEY_DIGITAL_SHARE, R.string.digital_filter_share),
        createSelector(FilterKey.KEY_DIGITAL_NO_SHARE, R.string.digital_filter_no_share)
    )

    private fun createSelector(
        key: String,
        @StringRes resId: Int,
        selected: Boolean = false
    ) = SelectorModel(key = key, label = context?.getString(resId).orEmpty(), isSelected = selected)
        .apply { model = key }

}
