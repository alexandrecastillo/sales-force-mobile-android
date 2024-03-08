package biz.belcorp.salesforce.modules.kpis.features.kpis.models

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

data class KpiDashboard(
    val title: String,
    val role: Rol,
    val kpis: List<KpiModel>
)
