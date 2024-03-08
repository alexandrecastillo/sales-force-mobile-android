package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.UsuarioMadre
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoAGrabar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.Reconocimientos
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.MadreUsuarioRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doInParallel
import io.reactivex.Single

class AdministrarReconocimientosUseCase(
    private val reconocimientosRepository: ReconocimientoRepository,
    private val madreUsuarioRepository: MadreUsuarioRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerListaReconocimientos(subscriber: BaseSingleObserver<Reconocimientos>) {
        val single = reconocimientosRepository.recuperarLista()
            .doInParallel(obtenerMadre())
            .map { Reconocimientos(it.second, it.first) }
        execute(single, subscriber)
    }

    fun reconocer(reconocimiento: ReconocimientoAGrabar, subscriber: BaseCompletableObserver) {
        val completable = reconocimientosRepository.reconocer(reconocimiento)
            .doAfterTerminate { sincronizar() }
        execute(completable, subscriber)
    }

    private fun obtenerMadre(): Single<UsuarioMadre> {
        return doOnSingle {
            requireNotNull(madreUsuarioRepository.recuperar())
        }
    }

    private fun sincronizar() {
        val completable = reconocimientosRepository.sincronizar()
        execute(completable, SincronizacionObserver())
    }

    private class SincronizacionObserver : BaseCompletableObserver() {
        override fun onError(e: Throwable) = e.printStackTrace()
    }
}
