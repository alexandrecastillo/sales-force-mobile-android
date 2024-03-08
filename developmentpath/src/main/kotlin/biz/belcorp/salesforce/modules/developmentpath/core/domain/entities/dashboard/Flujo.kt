package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dashboard

sealed class Flujo(val idPlan: Long)

class FlujoSE(idPlan: Long) : Flujo(idPlan)

class FlujoGZ(idPlan: Long) : Flujo(idPlan)

class FlujoGR(idPlan: Long) : Flujo(idPlan)

class FlujoDV(idPlan: Long) : Flujo(idPlan)

class FlujoNoDefinido(idPlan: Long) : Flujo(idPlan)