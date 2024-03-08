package biz.belcorp.salesforce.core.data.repository.searchfilters.dto

import biz.belcorp.salesforce.core.entities.sql.filtros.TipoEstadoEntity
import biz.belcorp.salesforce.core.entities.sql.filtros.TipoPedidoEntity
import biz.belcorp.salesforce.core.entities.sql.filtros.TipoSaldoEntity
import biz.belcorp.salesforce.core.entities.sql.filtros.TipoSegmentoEntity
import com.google.gson.annotations.SerializedName

class SearchFiltersDataResponse {

    @SerializedName("tiposEstados")
    var tipoEstadoList: List<TipoEstadoEntity> = emptyList()

    @SerializedName("tiposSegmentos")
    var tipoSegmentoList: List<TipoSegmentoEntity> = emptyList()

    @SerializedName("tiposPedidos")
    var tipoPedidoList: List<TipoPedidoEntity> = emptyList()

    @SerializedName("tiposSaldos")
    var tipoSaldoList: List<TipoSaldoEntity> = emptyList()

}
