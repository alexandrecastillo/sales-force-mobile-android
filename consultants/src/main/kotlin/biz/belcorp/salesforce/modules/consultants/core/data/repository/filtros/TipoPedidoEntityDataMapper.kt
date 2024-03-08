package biz.belcorp.salesforce.modules.consultants.core.data.repository.filtros

import biz.belcorp.salesforce.core.entities.sql.filtros.TipoPedidoEntity
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.TipoPedido

class TipoPedidoEntityDataMapper {

    fun parse(entity: TipoPedidoEntity?): TipoPedido? {
        var pojo: TipoPedido? = null

        if (entity != null) {
            pojo = TipoPedido()
            pojo.idPedido = entity.idPedido
            pojo.descripcion = entity.descripcion
        }

        return pojo
    }

    fun parse(collection: Collection<TipoPedidoEntity>): List<TipoPedido> {
        val list = ArrayList<TipoPedido>()
        var pojo: TipoPedido?
        for (entity in collection) {
            pojo = parse(entity)
            if (pojo != null) {
                list.add(pojo)
            }
        }
        return list
    }

}
