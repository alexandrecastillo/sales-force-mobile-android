package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.desempenio

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.desempenio.DesempenioCampanias

interface DesempenioRepository {
    fun obtener(llaveUA: LlaveUA): List<DesempenioCampanias>
}
