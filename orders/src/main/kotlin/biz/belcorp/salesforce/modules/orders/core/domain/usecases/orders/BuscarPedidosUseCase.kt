package biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders

import biz.belcorp.salesforce.base.utils.isSe
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.modules.orders.core.domain.contants.Constants.ESTADO_PEDIDO_DEFAULT
import biz.belcorp.salesforce.modules.orders.core.domain.contants.Constants.SEGMENTO_ID_DEFAULT
import biz.belcorp.salesforce.modules.orders.core.domain.contants.Constants.TIPO_FILTRO_DEFAULT
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.FiltroPedidosWeb
import biz.belcorp.salesforce.modules.orders.core.domain.entities.orders.OrderWebResult
import biz.belcorp.salesforce.modules.orders.core.domain.repository.PedidosWebRepository


class BuscarPedidosUseCase(
    private val sesionRepository: SessionRepository,
    private val pedidosWebRepository: PedidosWebRepository
) {

    private val sesion by lazy { sesionRepository.getSession() }

    suspend fun buscarPedidos(filtroPedidosWeb: FiltroPedidosWeb): OrderWebResult {

        filtroPedidosWeb.pais = sesion?.countryIso
        filtroPedidosWeb.region = sesion?.region
        filtroPedidosWeb.zona = sesion?.zona

        val resultados = if (sesion?.rol?.isSe() == true) {
            pedidosWebRepository.buscarPedidosSE(completarParaSE(filtroPedidosWeb))
        } else {
            pedidosWebRepository.buscarPedidos(filtroPedidosWeb)
        }

        return formatearPorPais(resultados)
    }

    private fun completarParaSE(filtroPedidosWeb: FiltroPedidosWeb): FiltroPedidosWeb {
        return filtroPedidosWeb.apply {
            consultoraLiderId = sesion?.person?.id?.toString()
            seccion = sesion?.seccion
            estadoPedido = estadoPedido ?: ESTADO_PEDIDO_DEFAULT
            segmentoId = segmentoId ?: SEGMENTO_ID_DEFAULT
            tipoFiltro = tipoFiltro ?: TIPO_FILTRO_DEFAULT
        }
    }

    private fun formatearPorPais(result: OrderWebResult): OrderWebResult {
        return when (sesion?.pais) {
            Pais.ECUADOR -> result.apply {
                orderWebList.forEach {
                    it.invertirNombre()
                }
            }
            else -> result
        }
    }

}
