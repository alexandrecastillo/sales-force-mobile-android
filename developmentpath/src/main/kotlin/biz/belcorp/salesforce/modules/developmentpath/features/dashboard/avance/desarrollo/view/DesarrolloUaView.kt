package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd

interface DesarrolloUaView {
    fun pintarTexto(uA: String, campania: String)
    fun irADesarrolloComportamientosAlHacerClickEnCard()
    fun irADesarrolloRegionAlHacerClickEnCard(infoPlan: InfoPlanRdd)
    fun irADesarrolloPaisAlHacerClickEnCard()
}
