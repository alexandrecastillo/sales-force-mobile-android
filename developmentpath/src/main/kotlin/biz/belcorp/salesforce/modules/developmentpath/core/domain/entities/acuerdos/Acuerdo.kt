package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import java.util.*

class Acuerdo(
    val id: Long = ID_NUEVO,
    var contenido: String,
    val fechaCreacion: Date,
    val codigoCampania: String,
    var cumplido: Boolean
) {

    companion object {
        const val ID_NUEVO = -1L
    }

    class ModeloCreacion(
        val contenido: String,
        val codigoCampania: String,
        val unidadAdministrativa: LlaveUA,
        val fecha: Date
    )
}
