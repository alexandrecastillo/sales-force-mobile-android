package biz.belcorp.salesforce.modules.consultants.features.search.models

import biz.belcorp.salesforce.core.features.search.models.TipoEstadoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSaldoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSegmentoModel

data class FiltrosBusquedaModel(
    var tipoEstadoList: List<TipoEstadoModel>,
    var tipoPedidoList: List<TipoPedidoModel>,
    var tipoSaldoList: List<TipoSaldoModel>,
    var tipoSegmentoList: List<TipoSegmentoModel>
)
