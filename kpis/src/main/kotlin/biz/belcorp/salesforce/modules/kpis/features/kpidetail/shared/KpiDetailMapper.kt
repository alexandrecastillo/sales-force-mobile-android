package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared

import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiDetailParams
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters

class KpiDetailMapper {
    fun map(params: KpiDetailParams) = with(params) {
        KpiFragmentParameters(rol, periodType, uaKey, kpiType)
    }
}
