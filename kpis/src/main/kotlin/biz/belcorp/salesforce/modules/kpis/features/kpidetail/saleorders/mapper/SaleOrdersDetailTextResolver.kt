package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.mapper

import android.content.Context
import biz.belcorp.salesforce.core.base.BaseTextResolver
import biz.belcorp.salesforce.modules.kpis.R

class SaleOrdersDetailTextResolver(private val context: Context) : BaseTextResolver() {

    fun formatTitle(vararg args: Any?): String {
        val format = context.getString(R.string.sale_orders_sale_title_multi_profile)
        return stringFormat(format, *args)
    }
}