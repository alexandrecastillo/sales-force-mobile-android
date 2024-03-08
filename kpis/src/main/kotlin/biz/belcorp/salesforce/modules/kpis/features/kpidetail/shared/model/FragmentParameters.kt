package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model

import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.constants.PeriodType
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class KpiFragmentParameters(
    rol: Rol,
    @PeriodType val periodType: Int,
    var uaKey: LlaveUA,
    @KpiType val kpiType: Int
) : FragmentParameters(rol) {

    var isParent: Boolean = true

    fun clone() = KpiFragmentParameters(rol, periodType, uaKey, kpiType)

}
