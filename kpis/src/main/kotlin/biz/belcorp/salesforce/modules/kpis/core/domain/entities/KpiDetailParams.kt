package biz.belcorp.salesforce.modules.kpis.core.domain.entities

import biz.belcorp.salesforce.core.constants.KpiType
import biz.belcorp.salesforce.core.constants.PeriodType
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class KpiDetailParams(
    val uaKey: LlaveUA,
    val rol: Rol,
    @PeriodType val periodType: Int,
    @KpiType val kpiType: Int
)
