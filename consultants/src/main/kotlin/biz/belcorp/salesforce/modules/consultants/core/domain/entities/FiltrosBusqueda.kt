package biz.belcorp.salesforce.modules.consultants.core.domain.entities

import biz.belcorp.salesforce.core.domain.entities.search.TipoEstado
import biz.belcorp.salesforce.core.domain.entities.search.TipoSegmento

data class FiltrosBusqueda(
    var tipoEstadoList: List<TipoEstado>,
    var tipoPedidoList: List<TipoPedido>,
    var tipoSaldoList: List<TipoSaldo>,
    var tipoSegmentoList: List<TipoSegmento>
)
