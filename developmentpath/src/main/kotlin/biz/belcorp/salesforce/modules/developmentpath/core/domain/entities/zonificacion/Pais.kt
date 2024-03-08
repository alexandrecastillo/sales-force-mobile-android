package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion


import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.ResponsableUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorVentas

class Pais(
    codigo: String,
    campania: Campania,
    val directorVentas: DirectorVentas?,
    val regiones: List<RegionRdd>
) : UnidadAdministrativa(codigo, campania) {

    override val padre: UnidadAdministrativa? get() = null

    override val responsableUA: ResponsableUA? get() = directorVentas

    override val llave: LlaveUA
        get() = LlaveUA(
            null,
            null,
            null,
            null
        )
}
