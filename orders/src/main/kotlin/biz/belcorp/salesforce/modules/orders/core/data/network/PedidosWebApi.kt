package biz.belcorp.salesforce.modules.orders.core.data.network

import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto.BloqueoPedidoResponse
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto.BuscarPedidoRequest
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto.BuscarPedidoResponse
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.dto.PedidoWebResponse
import retrofit2.Response
import retrofit2.http.*

interface PedidosWebApi {

    @POST("FFVVOnlineService/api/pedidos/{order}")
    suspend fun bloqueo(
        @Path("order") orderId: Int,
        @Query("bloqueo") bloqueo: String,
        @Query("campania") campania: String?
    ): Response<BloqueoPedidoResponse>

    @GET("FFVVOnlineService/api/pedidos/{region}/{zona}/{campania}")
    suspend fun buscarPedidos(
        @Path("region") region: String,
        @Path("zona") zona: String,
        @Path("campania") campania: String,
        @QueryMap opciones: HashMap<String, String>
    ): Response<PedidoWebResponse>

    @POST("FFVVOnlineService/api/pedidos/getpedidosweb")
    suspend fun buscarPedidosSE(@Body request: BuscarPedidoRequest): Response<BuscarPedidoResponse>

}
