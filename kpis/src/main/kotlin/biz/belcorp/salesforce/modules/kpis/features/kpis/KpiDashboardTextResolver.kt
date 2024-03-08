package biz.belcorp.salesforce.modules.kpis.features.kpis

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.modules.kpis.R

class KpiDashboardTextResolver(private val context: Context) : BaseTextResolver() {

    fun formatTitleKpisDashboard(vararg args: Any?): String {
        val format = context.getString(R.string.kpis_title)
        return stringFormat(format, *args)
    }
}
