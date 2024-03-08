package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Feriado

interface FechaNoHabilRepository {
    fun obtener(llaveUA: LlaveUA): List<Feriado>
}
