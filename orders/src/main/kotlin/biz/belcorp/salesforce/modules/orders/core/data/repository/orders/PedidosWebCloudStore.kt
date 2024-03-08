package biz.belcorp.salesforce.modules.orders.core.data.repository.orders

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.modules.orders.core.data.network.PedidosWebApi
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto.BuscarPedidoResponse
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto.PedidoWebResponse
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.mappers.BuscarPedidosRequestMapper
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.FiltroPedidosWeb

class PedidosWebCloudStore(
    private val pedidosWebApi: PedidosWebApi,
    private val apiHelper: SafeApiCallHelper,
    private val pedidosRequestMapper: BuscarPedidosRequestMapper
) {

    companion object {

        private const val BLOQUEAR = "1"
        private const val DESBLOQUEAR = "0"

    }

    suspend fun buscarPedidosSE(filtros: FiltroPedidosWeb): BuscarPedidoResponse? {
        val request = pedidosRequestMapper.mapSE(filtros)
        return apiHelper.safeLegacyApiCall {
            pedidosWebApi.buscarPedidosSE(request)
        }
    }

    suspend fun buscarPedidos(request: FiltroPedidosWeb): PedidoWebResponse? {
        val opciones = pedidosRequestMapper.map(request)
        return apiHelper.safeLegacyApiCall {
            pedidosWebApi.buscarPedidos(
                region = request.region.orEmpty(),
                zona = request.zona.orEmpty(),
                campania = request.campania.orEmpty(),
                opciones = opciones
            )
        }
    }

    suspend fun bloquear(orderId: Int, campania: String?): Int {
        val response = apiHelper.safeLegacyApiCall {
            pedidosWebApi.bloqueo(
                orderId,
                BLOQUEAR, requireNotNull(campania)
            )
        }
        return requireNotNull(response).resultado
    }

    suspend fun desbloquear(orderId: Int, campania: String?): Int {
        val response = apiHelper.safeLegacyApiCall {
            pedidosWebApi.bloqueo(
                orderId,
                DESBLOQUEAR, requireNotNull(campania)
            )
        }
        return requireNotNull(response).resultado
    }

}
