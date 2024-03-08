package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.ResponsableUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd

class ConsultoraUaRdd(
    val consultora: ConsultoraRdd?,
    codigo: String,
    campania: Campania
) :
    UnidadAdministrativa(codigo, campania) {

    lateinit var seccion: SeccionRdd

    override val padre: UnidadAdministrativa? get() = seccion

    override val responsableUA: ResponsableUA? get() = consultora

    override val llave: LlaveUA
        get() = LlaveUA(
            seccion.zona.region.codigo,
            seccion.zona.codigo,
            seccion.codigo,
            consultora?.id
        )

}
