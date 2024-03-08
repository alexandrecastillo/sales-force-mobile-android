package biz.belcorp.salesforce.modules.kpis.features.kpis.models

import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

data class KpiDashboardParams(
    val role: Rol = Rol.NINGUNO,
    val uaKey: LlaveUA? = null,
    val person: Person? = null
)
