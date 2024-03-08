package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.mapa

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import java.io.Serializable

open class PersonaEnMapaModel(
    val personaId: Long,
    val personCode: String,
    val iniciales: String,
    val ubicacion: Ubicacion,
    var estaCerca: Boolean = false,
    val tieneAlgunaVisitaRegistrada: Boolean,
    val rol: Rol
) : Serializable {

    fun marcarComoCercana() {
        estaCerca = true
    }
}
