package biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders

import biz.belcorp.salesforce.modules.orders.core.domain.contants.Constants.BLOQUEO_RESULT_OK
import biz.belcorp.salesforce.modules.orders.core.domain.exceptions.BloqueoPedidoException
import biz.belcorp.salesforce.modules.orders.core.domain.repository.PedidosWebRepository


class DesbloquearPedidoUseCase(private val pedidosWebRepository: PedidosWebRepository) {

    suspend fun desbloquear(pedidoId: Int, campania: String?) {
        val result = pedidosWebRepository.unlock(pedidoId, campania)
        if (result != BLOQUEO_RESULT_OK) {
            throw BloqueoPedidoException()
        }
    }

}
