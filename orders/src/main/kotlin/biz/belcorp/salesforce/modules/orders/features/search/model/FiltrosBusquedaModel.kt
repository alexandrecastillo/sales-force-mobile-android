package biz.belcorp.salesforce.modules.orders.features.search.model

import biz.belcorp.salesforce.core.features.search.models.TipoEstadoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSaldoModel
import biz.belcorp.salesforce.core.features.search.models.TipoSegmentoModel


data class FiltrosBusquedaModel(
    var tipoEstadoList: List<TipoEstadoModel>,
    var tipoSaldoList: List<TipoSaldoModel>,
    var tipoSegmentoList: List<TipoSegmentoModel>,
    var seccionesList: List<SeccionModel>,
    var campaniasList: List<CampaniaModel>
)
