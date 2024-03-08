package biz.belcorp.salesforce.modules.kpis.utils

import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.base.utils.navigateTo
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingFragmentArgs
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiDetailFragmentArgs
import biz.belcorp.salesforce.base.R as BaseR

fun Fragment.navigateToBilling(uaKey: LlaveUA) {
    val args = BillingFragmentArgs(uaKey.roleAssociated, uaKey).toBundle()
    navigateTo(BaseR.id.globalToBilling, args)
}

fun Fragment.navigateToKpiDetail(
    @KpiType kpiType: Int = KpiType.NONE, kpiName: String
) {
    val args = KpiDetailFragmentArgs(kpiType, kpiName).toBundle()
    navigateTo(BaseR.id.globalToKpiDetail, args)
}
