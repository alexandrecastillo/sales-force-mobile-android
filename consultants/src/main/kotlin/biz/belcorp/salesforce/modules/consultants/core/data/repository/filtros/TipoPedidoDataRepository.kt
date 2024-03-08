package biz.belcorp.salesforce.modules.consultants.core.data.repository.filtros

import biz.belcorp.salesforce.modules.consultants.core.domain.entities.TipoPedido
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros.TipoPedidoRepository
import io.reactivex.Observable

class TipoPedidoDataRepository(
    private val dataStore: TipoPedidoDBDataStore,
    private val dataMapper: TipoPedidoEntityDataMapper
) : TipoPedidoRepository {

    override val all: Observable<List<TipoPedido>>
        get() {
            return dataStore
                .all
                .map { dataMapper.parse(it) }
        }

}
