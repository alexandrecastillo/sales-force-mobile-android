package biz.belcorp.salesforce.modules.consultants.features.search.mappers

import biz.belcorp.salesforce.modules.consultants.core.domain.entities.FiltrosBusqueda
import biz.belcorp.salesforce.modules.consultants.features.search.models.FiltrosBusquedaModel

class FiltrosBusquedaMapper(
    private val estadoMapper: TipoEstadoModelDataMapper,
    private val pedidoMapper: TipoPedidoModelDataMapper,
    private val saldoMapper: TipoSaldoModelDataMapper,
    private val segmentoMapper: TipoSegmentoModelDataMapper
) {

    fun parse(filtros: FiltrosBusqueda): FiltrosBusquedaModel {
        return FiltrosBusquedaModel(
            tipoEstadoList = estadoMapper.parse(filtros.tipoEstadoList),
            tipoPedidoList = pedidoMapper.parse(filtros.tipoPedidoList),
            tipoSaldoList = saldoMapper.parse(filtros.tipoSaldoList),
            tipoSegmentoList = segmentoMapper.parse(filtros.tipoSegmentoList)
        )
    }

}
