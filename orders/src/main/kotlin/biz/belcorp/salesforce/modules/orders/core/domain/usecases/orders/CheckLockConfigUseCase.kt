package biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders

import biz.belcorp.salesforce.modules.orders.core.domain.contants.Constants.ACTIVAR_BLOQUEO_PEDIDO
import biz.belcorp.salesforce.modules.orders.core.domain.repository.PedidosWebRepository


class CheckLockConfigUseCase(private val pedidosWebRepository: PedidosWebRepository) {

    suspend fun checkConfig(): Int {
        val value = pedidosWebRepository.checkLockConfig()
        return value?.toIntOrNull() ?: ACTIVAR_BLOQUEO_PEDIDO
    }

}
