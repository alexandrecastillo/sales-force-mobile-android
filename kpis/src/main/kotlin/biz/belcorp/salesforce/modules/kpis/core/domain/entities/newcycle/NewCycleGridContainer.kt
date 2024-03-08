package biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

data class NewCycleGridContainer(
    var childIndicatorList: List<NewCycleIndicator>,
    val isBilling: Boolean,
    val role: Rol,
    val isParent: Boolean
)
