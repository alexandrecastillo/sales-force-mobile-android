package biz.belcorp.salesforce.modules.orders.core.data.repository.orders

import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.mappers.BuscarPedidoResponseDataMapper
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.mappers.PedidoResponseDataMapper
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.FiltroPedidosWeb
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.OrderWebResult
import biz.belcorp.salesforce.modules.orders.core.domain.repository.PedidosWebRepository


class PedidosWebDataRepository(
    private val pedidosWebDataStore: PedidosWebDataStore,
    private val pedidosWebCloudStore: PedidosWebCloudStore,
    private val pedidoWebMapper: PedidoResponseDataMapper,
    private val buscarPedidoWebMapper: BuscarPedidoResponseDataMapper
) :
    PedidosWebRepository {

    override suspend fun buscarPedidosSE(filtroPedidosWeb: FiltroPedidosWeb): OrderWebResult {
        val response = pedidosWebCloudStore.buscarPedidosSE(filtroPedidosWeb)
        return buscarPedidoWebMapper.parse(requireNotNull(response))
    }

    override suspend fun buscarPedidos(filtroPedidosWeb: FiltroPedidosWeb): OrderWebResult {
        val response = pedidosWebCloudStore.buscarPedidos(filtroPedidosWeb)
        return pedidoWebMapper.parse(requireNotNull(response?.resultado))
    }

    override suspend fun lock(orderId: Int, campania: String?): Int {
        return pedidosWebCloudStore.bloquear(orderId, campania)
    }

    override suspend fun unlock(orderId: Int, campania: String?): Int {
        return pedidosWebCloudStore.desbloquear(orderId, campania)
    }

    override suspend fun checkLockConfig(): String? {
        return pedidosWebDataStore.getLockParam()?.valor
    }

}
