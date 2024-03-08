package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRdd
import biz.belcorp.salesforce.core.utils.primerPadre
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doInParallel
import io.reactivex.Single

class RecuperarEventoUseCase(
    private val eventoRepository: EventoRepository,
    private val recuperarRelacionObservadorEventoUseCase: RecuperarRelacionObservadorEventoUseCase,
    private val sesionManager: SessionPersonRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun recuperar(eventoXUaId: Long, observer: BaseSingleObserver<Response>) {
        val single =
            eventoRepository.recuperar(eventoXUaId)
                .doInParallel(recuperarRelacionObservadorEventoUseCase.determinarTipo(eventoXUaId))
                .doInParallel(recuperarRolMadre())
                .map {
                    Response(
                        evento = it.first.first,
                        relacion = it.first.second,
                        rolMadre = it.second
                    )
                }
        execute(single, observer)

    }

    private fun recuperarRolMadre(): Single<Rol> {
        return doOnSingle { sesionManager.get()?.rol?.primerPadre() ?: Rol.NINGUNO }
    }

    class Response(
        val evento: EventoRdd,
        val relacion: RecuperarRelacionObservadorEventoUseCase.Tipo,
        val rolMadre: Rol
    )
}
