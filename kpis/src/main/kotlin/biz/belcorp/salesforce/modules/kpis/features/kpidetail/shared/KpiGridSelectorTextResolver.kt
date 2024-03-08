package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isDV
import biz.belcorp.salesforce.core.utils.isGR
import biz.belcorp.salesforce.modules.kpis.R

class KpiGridSelectorTextResolver(val context: Context) : BaseTextResolver() {

    fun getAdvancementByUa(role: Rol, vararg args: Any): String {
        val uaSegmentText = getUaNameByRole(role)
        return getAdvancementText(uaSegmentText, *args)
    }

    private fun getAdvancementText(vararg args: Any): String {
        val format = context.getString(R.string.subtitle_sales_orders_progress)
        return stringFormat(format, *args)
    }

    private fun getUaNameByRole(role: Rol): String {
        val intRes = when {
            role.isDV() -> R.string.header_single_dv
            role.isGR() -> R.string.header_single_gr
            else -> R.string.header_single_gz
        }
        return context.getString(intRes).decapitalize()
    }
}
