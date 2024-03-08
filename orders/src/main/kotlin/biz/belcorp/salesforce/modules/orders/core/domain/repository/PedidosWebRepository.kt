package biz.belcorp.salesforce.modules.orders.core.domain.repository


import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.FiltroPedidosWeb
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.OrderWebResult


interface PedidosWebRepository {

    suspend fun buscarPedidosSE(filtroPedidosWeb: FiltroPedidosWeb): OrderWebResult

    suspend fun buscarPedidos(filtroPedidosWeb: FiltroPedidosWeb): OrderWebResult

    suspend fun lock(orderId: Int, campania: String?): Int

    suspend fun unlock(orderId: Int, campania: String?): Int

    suspend fun checkLockConfig(): String?

}
