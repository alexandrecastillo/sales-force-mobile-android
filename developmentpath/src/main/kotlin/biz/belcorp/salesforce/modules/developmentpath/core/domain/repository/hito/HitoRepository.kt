package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.hito

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Hito
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.HitoEnRegion

interface HitoRepository {
    fun obtenerPorRegion(): List<HitoEnRegion>
    fun obtenerPorZona(codigoZona: String): List<Hito>
}
