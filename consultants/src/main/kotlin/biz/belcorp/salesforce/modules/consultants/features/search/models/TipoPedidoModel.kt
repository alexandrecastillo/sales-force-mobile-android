package biz.belcorp.salesforce.modules.consultants.features.search.models

import biz.belcorp.salesforce.modules.consultants.core.domain.entities.TipoPedido

class TipoPedidoModel {

    var idPedido: Int? = null

    var descripcion: String? = null

    fun parse(pojo: TipoPedido): TipoPedidoModel {
        this.idPedido = pojo.idPedido
        this.descripcion = pojo.descripcion
        return this
    }

}
