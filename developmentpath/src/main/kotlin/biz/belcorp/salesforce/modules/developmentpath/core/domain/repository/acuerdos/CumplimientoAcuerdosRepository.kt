package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado.CumplimientoPrecalculado

interface CumplimientoAcuerdosRepository {
    fun obtener(llaveUA: LlaveUA): List<CumplimientoPrecalculado>
}
