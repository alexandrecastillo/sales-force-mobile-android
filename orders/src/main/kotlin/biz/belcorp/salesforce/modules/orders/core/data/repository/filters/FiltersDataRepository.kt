package biz.belcorp.salesforce.modules.orders.core.data.repository.filters

import biz.belcorp.salesforce.core.domain.entities.search.TipoEstado
import biz.belcorp.salesforce.core.domain.entities.search.TipoSegmento
import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.modules.orders.core.domain.entities.filters.TipoSaldo
import biz.belcorp.salesforce.modules.orders.core.domain.repository.FiltersRepository


class FiltersDataRepository(
    private val filtersDataStore: FiltersDataStore,
    private val filtersDataMapper: FiltersDataMapper
) : FiltersRepository {

    override suspend fun getTipoEstadoFilter(): List<TipoEstado> {
        val list = filtersDataStore.getTipoEstadoFilter()
        return filtersDataMapper.mapEstadoList(list)
    }

    override suspend fun getTipoSaldoFilter(): List<TipoSaldo> {
        val list = filtersDataStore.getTipoSaldoFilter()
        return filtersDataMapper.mapSaldoList(list)
    }

    override suspend fun getTipoSegmentoFilter(): List<TipoSegmento> {
        val list = filtersDataStore.getTipoSegmentoFilter()
        return filtersDataMapper.mapSegmentoList(list)
    }

    override suspend fun getSeccionesFilter(): List<Seccion> {
        val list = filtersDataStore.getSeccionesFilter()
        return filtersDataMapper.mapSeccionList(list)
    }
}
