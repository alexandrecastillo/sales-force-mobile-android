package biz.belcorp.salesforce.modules.orders.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.search.TipoEstado
import biz.belcorp.salesforce.core.domain.entities.search.TipoSegmento
import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.modules.orders.core.domain.entities.filters.TipoSaldo

interface FiltersRepository {

    suspend fun getTipoEstadoFilter(): List<TipoEstado>
    suspend fun getTipoSaldoFilter(): List<TipoSaldo>
    suspend fun getTipoSegmentoFilter(): List<TipoSegmento>
    suspend fun getSeccionesFilter(): List<Seccion>

}
