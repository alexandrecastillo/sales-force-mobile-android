package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.AcuerdosRepository
import io.reactivex.Completable
import io.reactivex.CompletableObserver

class ModificarAcuerdosUseCase(
    private val acuerdosRepository: AcuerdosRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun editar(request: EditarRequest) {
        val completable = Completable.create { emitter ->
            val acuerdo = requireNotNull(acuerdosRepository.obtener(request.acuerdoId))

            acuerdo.contenido = request.nuevoContenido

            acuerdosRepository.editar(acuerdo)

            emitter.onComplete()
        }

        execute(completable, request.subscriber)
    }

    fun eliminar(request: EliminarRequest) {
        val completable = Completable.create { emitter ->
            acuerdosRepository.eliminar(request.acuerdoId)

            emitter.onComplete()
        }

        execute(completable, request.subscriber)
    }

    class EditarRequest(
        val acuerdoId: Long,
        val nuevoContenido: String,
        val subscriber: CompletableObserver
    )

    class EliminarRequest(
        val acuerdoId: Long,
        val subscriber: CompletableObserver
    )
}
