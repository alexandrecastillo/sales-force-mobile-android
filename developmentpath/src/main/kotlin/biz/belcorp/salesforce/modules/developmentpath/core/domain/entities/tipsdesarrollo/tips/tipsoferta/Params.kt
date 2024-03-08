package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

open class Params(
    val personaId: Long = 0L,
    val personaRol: Rol,
    val offline: Boolean
) {
    lateinit var ua: LlaveUA
    lateinit var documento: String
    lateinit var pais: String
    lateinit var campania: String
}
