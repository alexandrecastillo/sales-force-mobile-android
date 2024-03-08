package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.ResponsableUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd

class ZonaRdd(
    codigo: String,
    campania: Campania,
    secciones: List<SeccionRdd>,
    var gerenteZona: GerenteZonaRdd?,
    val focos: MutableList<Foco>,
    val planId: Long?,
    val habilidades: MutableList<Habilidad>,
    val visitasPlanificadas: Int = 0,
    val visitasRegistradas: Int = 0
) : UnidadAdministrativa(codigo, campania) {

    lateinit var region: RegionRdd
    override val padre: UnidadAdministrativa? get() = region
    override val responsableUA: ResponsableUA? get() = gerenteZona

    override val llave: LlaveUA
        get() = LlaveUA(
            codigoRegion = region.codigo,
            codigoZona = codigo,
            codigoSeccion = null,
            consultoraId = null
        )
}
