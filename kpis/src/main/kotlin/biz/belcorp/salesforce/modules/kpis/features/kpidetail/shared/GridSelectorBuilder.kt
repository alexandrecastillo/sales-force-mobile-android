package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared

import android.content.Context
import androidx.annotation.StringRes
import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SaleOrderGridType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.filtergrid.types.CapitalizationGridType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.filtergrid.types.NewCycleGridType

object GridSelectorBuilder {

    private var context: Context? = null
    private var kpiType: Int = KpiType.NONE

    fun init(context: Context): GridSelectorBuilder {
        this.context = context
        return this
    }

    fun with(@KpiType kpiType: Int): GridSelectorBuilder {
        this.kpiType = kpiType
        return this
    }

    fun build(): List<SelectorModel> {
        return when (kpiType) {
            KpiType.SALE_ORDERS -> createSaleOrdersFilters()
            KpiType.CAPITALIZATION -> createCapitalizationFilters()
            KpiType.NEW_CYCLES -> createNewCycleFilters()
            else -> emptyList()
        }
    }

    private fun createSaleOrdersFilters() = listOf(
        createSelector(SaleOrderGridType.SALES, R.string.sale, selected = true),
        createSelector(SaleOrderGridType.ORDERS, R.string.orders),
        createSelector(SaleOrderGridType.ACTIVITY, R.string.activity),
        createSelector(SaleOrderGridType.ACTIVES_RETENTION, R.string.actives_retention)
    )

    private fun createCapitalizationFilters() = listOf(
        createSelector(CapitalizationGridType.PEGS, R.string.pegs, selected = true),
        createSelector(CapitalizationGridType.CAPITALIZATION, R.string.capitalization)
    )

    private fun createNewCycleFilters() = listOf(
        createSelector(NewCycleGridType.LOW_VALUE, R.string.text_news, selected = true),
        createSelector(NewCycleGridType.HIGH_VALUE, R.string.text_news_high_value)
    )

    private fun createSelector(
        key: String,
        @StringRes resId: Int,
        selected: Boolean = false
    ) = SelectorModel(key = key, label = context?.getString(resId).orEmpty(), isSelected = selected)
        .apply { model = key }

}
