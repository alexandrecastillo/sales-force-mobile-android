package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.historicos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.HistoricoRegion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraficosGrRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import io.reactivex.Single

class HistoricaRegionBaseUseCase(
    private val personaRepository: RddPersonaRepository,
    private val graficosGrRepository: GraficosGrRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun ejecutar(params: Params) {
        val single = recuperarUa(params.gerenteId, params.rol)
            .flatMap { graficosGrRepository.obtenerGrafico(it, params.tipoGrafico) }
        execute(single, params.observer)
    }

    private fun recuperarUa(gerenteId: Long, rol: Rol): Single<LlaveUA> {
        return doOnSingle {
            val gerente = requireNotNull(personaRepository.recuperarPersonaPorId(gerenteId, rol))
            requireNotNull((gerente.llaveUA))
        }
    }

    class Params(
        val gerenteId: Long,
        val rol: Rol,
        val tipoGrafico: TipoGrafico,
        val observer: BaseSingleObserver<HistoricoRegion>
    )
}
