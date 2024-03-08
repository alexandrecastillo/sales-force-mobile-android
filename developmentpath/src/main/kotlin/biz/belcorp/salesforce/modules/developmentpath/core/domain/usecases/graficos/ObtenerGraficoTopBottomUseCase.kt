package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TopBottomHistorico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraficosGrRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import io.reactivex.Single

class ObtenerGraficoTopBottomUseCase(
    private val graficosGrRepository: GraficosGrRepository,
    private val personaRepository: RddPersonaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun obtener(
        personaId: Long, rol: Rol, tipoGrafico: TipoGrafico,
        subscriber: BaseSingleObserver<TopBottomHistorico>
    ) {
        val single = obtenerUa(personaId, rol)
            .flatMap { llaveUa ->
                graficosGrRepository
                    .obtenerBloqueTopBottomPorTipoGrafico(llaveUa, tipoGrafico)
            }
        execute(single, subscriber)
    }

    private fun obtenerUa(personaId: Long, rol: Rol): Single<LlaveUA> {
        return doOnSingle {
            requireNotNull(personaRepository.recuperarPersonaPorId(personaId, rol)?.llaveUA)
        }
    }
}
