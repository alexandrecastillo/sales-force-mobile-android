package biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders


import biz.belcorp.salesforce.modules.orders.core.domain.contants.Constants.BLOQUEO_RESULT_OK
import biz.belcorp.salesforce.modules.orders.core.domain.exceptions.BloqueoPedidoException
import biz.belcorp.salesforce.modules.orders.core.domain.repository.PedidosWebRepository


class BloquearPedidoUseCase(private val pedidosWebRepository: PedidosWebRepository) {

    suspend fun bloquear(pedidoId: Int, campania: String?) {
        val result = pedidosWebRepository.lock(pedidoId, campania)
        if (result != BLOQUEO_RESULT_OK) {
            throw BloqueoPedidoException()
        }
    }

}
