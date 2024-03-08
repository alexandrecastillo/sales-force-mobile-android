package biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros

import biz.belcorp.salesforce.modules.consultants.core.domain.entities.TipoPedido
import io.reactivex.Observable

interface TipoPedidoRepository {
    val all: Observable<List<TipoPedido>>
}
