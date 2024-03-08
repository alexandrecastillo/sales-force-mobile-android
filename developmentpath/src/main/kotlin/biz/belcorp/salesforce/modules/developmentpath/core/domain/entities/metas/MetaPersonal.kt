package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import java.util.*

class MetaPersonal(
    val metaId: Long,
    val personaId: Long,
    val descripcion: String,
    val fecha: Calendar,
    var campania: String = "",
    var region: String = "-",
    var zona: String = "-",
    var seccion: String = "-"
) {
    fun asignarUa(llaveUA: LlaveUA) {
        region = llaveUA.codigoRegion ?: "-"
        zona = llaveUA.codigoZona ?: "-"
        seccion = llaveUA.codigoSeccion ?: "-"
    }
}
