package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades

import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.HabilidadesAsyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.reconocer.HabilidadesReconoceRepository
import io.reactivex.Completable

class SincronizadorReconocerHabilidades(
    private val habilidadesReconoceRepository: HabilidadesReconoceRepository,
    private val habilidadesAsynRepository: HabilidadesAsyncRepository
) {

    fun sincronizar(): Completable {
        val pendientes = habilidadesReconoceRepository.obtenerReconocidasNoEnvidas()
        if (pendientes.isNotEmpty()) {
            return habilidadesAsynRepository
                .guardarHabilidadesReconocidas(pendientes)
                .doOnComplete { habilidadesReconoceRepository.marcarReconocidasComoEnviadas() }
        }
        return Completable.complete()
    }
}
