package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades

import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.HabilidadesAsyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.asignar.HabilidadesAsignaRepository
import io.reactivex.Completable

class SincronizadorAsignarHabilidades(
    private val habilidadesAsignaRepository: HabilidadesAsignaRepository,
    private val habilidadesAsynRepository: HabilidadesAsyncRepository
) {

    fun sincronizar(): Completable {
        val pendientes = habilidadesAsignaRepository.obtenerDetallesNoEnviados()
        if (pendientes.isNotEmpty()) {
            return habilidadesAsynRepository
                .guardarHabilidadesAsignadas(pendientes)
                .doOnComplete {
                    habilidadesAsignaRepository.marcarDetallesComoEnviados()
                }
        }
        return Completable.complete()
    }
}
