package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.notas.perfil

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import java.util.*

class Nota(
    val id: Long = ID_NUEVO,
    val descripcion: String,
    val fecha: Calendar,
    val personaId: Long,
    var campania: String = ""
) {
    lateinit var unidadAdministrativa: LlaveUA

    companion object {
        const val ID_NUEVO = -1L
    }
}
