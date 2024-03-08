package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class PeticionRutaOptima(
    val origen: String,
    val destino: String,
    val optimizacion: Boolean = true,
    val modo: String = "walking",
    val waypoints: String = "",
    val rol: Rol
)
