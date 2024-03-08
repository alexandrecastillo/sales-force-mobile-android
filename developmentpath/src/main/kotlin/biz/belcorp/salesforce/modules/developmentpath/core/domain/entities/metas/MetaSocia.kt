package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import java.util.*

class MetaSocia(
    val id: Long,
    val descripcion: String,
    var fecha: Date,
    var campania: String = "-",
    var region: String = "-",
    var zona: String = "-",
    var seccion: String = "-",
    var personaId: Long = -1L
) {

    fun crearUa(llaveUA: LlaveUA): MetaSocia {
        region = llaveUA.codigoRegion.aGuionSiEsNullOVacio()
        zona = llaveUA.codigoZona.aGuionSiEsNullOVacio()
        seccion = llaveUA.codigoSeccion.aGuionSiEsNullOVacio()
        return this
    }

    fun crearCampania(unidadAdministrativa: UnidadAdministrativa): MetaSocia {
        campania = unidadAdministrativa.campania.codigo
        return this
    }

    fun crearIdPersona(personaId: Long): MetaSocia {
        this.personaId = personaId
        return this
    }

    companion object {
        fun crearSinDatos() = MetaSocia(-1, "", Date())
    }
}
