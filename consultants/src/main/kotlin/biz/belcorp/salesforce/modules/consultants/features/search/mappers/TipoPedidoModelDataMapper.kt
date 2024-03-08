package biz.belcorp.salesforce.modules.consultants.features.search.mappers

import biz.belcorp.salesforce.modules.consultants.core.domain.entities.TipoPedido
import biz.belcorp.salesforce.modules.consultants.features.search.models.TipoPedidoModel

class TipoPedidoModelDataMapper {

    fun parse(collection: Collection<TipoPedido>?): List<TipoPedidoModel> {
        return if (collection != null && !collection.isEmpty()) {
            collection.mapTo(ArrayList()) { TipoPedidoModel().parse(it) }
        } else {
            emptyList()
        }
    }

    fun parse(pojo: TipoPedido): TipoPedidoModel {
        return TipoPedidoModel().parse(pojo)
    }

}
