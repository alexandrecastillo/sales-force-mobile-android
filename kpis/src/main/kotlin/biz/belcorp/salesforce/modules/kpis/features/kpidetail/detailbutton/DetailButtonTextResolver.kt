package biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton

import android.content.Context
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.features.utils.CoreTextResolver
import biz.belcorp.salesforce.modules.kpis.R.string.*

class DetailButtonTextResolver(context: Context) : CoreTextResolver(context) {
    fun getThirdPersonSeTitle(@KpiType kpiType: Int): String {
        val resId = when (kpiType) {
            KpiType.COLLECTION -> title_handle_cs_with_debts
            KpiType.CAPITALIZATION -> title_handle_pegs
            KpiType.SALE_ORDERS -> title_handle_actives
            KpiType.NEW_CYCLES -> text_new_cycle_sale_title_gz
            else -> title_handle_cs_with_debts
        }
        return getString(resId)
    }

    fun getDefaultTitle(@KpiType kpiType: Int, campaign: String): String {
        return when (kpiType) {
            KpiType.COLLECTION -> getString(title_handle_your_cs_with_debts)
            KpiType.CAPITALIZATION -> getString(retention_capi_title)
            KpiType.SALE_ORDERS ->
                getString(title_handle_consultants_with_orders, getCampaignFormatted(campaign))
            KpiType.NEW_CYCLES -> getString(text_new_cycle_sale_title)
            else -> getString(title_handle_your_cs_with_debts)
        }
    }

    fun getBillingButtonTitle(): String {
        return getString(title_new_cycle_billing_detail)
    }
}
