package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.ResponsableUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd

class SeccionRdd(
    codigo: String,
    campania: Campania,
    var sociaEmpresaria: SociaEmpresariaRdd?,
    consultoras: List<ConsultoraRdd>,
    val nivel: String?,
    val planId: Long?,
    val visitasProgramadasInicialmente: Int,
    val visitasRegistradas: Int,
    val visitedDays:Int = 0,
) : UnidadAdministrativa(codigo, campania) {

    lateinit var zona: ZonaRdd

    override val padre: UnidadAdministrativa? get() = zona

    override val responsableUA: ResponsableUA? get() = sociaEmpresaria

    override val llave: LlaveUA
    get() = LlaveUA(
        zona.region.codigo,
        zona.codigo,
        codigo,
        null
    )

    val progreso: Double = 100.0 * visitasRegistradas / visitasProgramadasInicialmente

    val fuePlanificada get() = planId != null
}
