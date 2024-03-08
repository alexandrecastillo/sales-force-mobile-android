package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.productivas

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.ZonasProductividad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraficosGrRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import io.reactivex.Single

class ProductivasU3CUseCase(
    private val personaRepository: RddPersonaRepository,
    private val graficosGrRepository: GraficosGrRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun ejecutar(gerenteId: Long, observer: BaseSingleObserver<ZonasProductividad>) {
        val single = recuperarLlaveUa(gerenteId)
            .flatMap { graficosGrRepository.obtenerBloqueProductiva(it) }
        execute(single, observer)
    }

    private fun recuperarLlaveUa(gerenteId: Long): Single<LlaveUA> {
        return doOnSingle {
            val gerente = requireNotNull(
                personaRepository.recuperarPersonaPorId(
                    gerenteId,
                    Rol.GERENTE_REGION
                )
            )
            requireNotNull((gerente as? GerenteRegionRdd)?.llaveUA)
        }
    }
}
