package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.GuardadoFocosAsyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.GuardadoFocosRepository
import io.reactivex.Completable

class SincronizadorFocos(private val guardadoFocosRepository: GuardadoFocosRepository,
                         private val guardadoFocosAsyncRepository: GuardadoFocosAsyncRepository
) {

    fun sincronizar(): Completable {
        val pendientes = guardadoFocosRepository.obtenerDetallesNoEnviados()

        if (pendientes.isNotEmpty()) {
            return guardadoFocosAsyncRepository
                .guardar(pendientes)
                .doOnComplete {
                    guardadoFocosRepository.marcarDetallesComoEnviados()
                }
        }

        return Completable.complete()
    }
}
