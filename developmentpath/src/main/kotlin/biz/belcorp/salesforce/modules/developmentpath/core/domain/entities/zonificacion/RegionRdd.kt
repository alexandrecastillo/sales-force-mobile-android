package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.ResponsableUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd

class RegionRdd(
    codigo: String,
    campania: Campania,
    var gerenteRegion: GerenteRegionRdd?,
    zonas: List<ZonaRdd>,
    val focos: MutableList<Foco>,
    val habilidades: MutableList<Habilidad>,
    val visitasRealizadas: Int = 0,
    val visitasPlanificadas: Int = 0,
    val planId: Long,
    val planValido: Boolean
) : UnidadAdministrativa(codigo, campania) {

    override val padre: UnidadAdministrativa? get() = null

    override val responsableUA: ResponsableUA? get() = gerenteRegion

    override val llave: LlaveUA
    get() = LlaveUA(
        this.codigo,
        null,
        null,
        null
    )
}
