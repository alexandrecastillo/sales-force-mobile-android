package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.alarmas.AlarmasWorker
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Alarma
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Completable
import io.reactivex.CompletableObserver

class ProgramarAlarmasEventosUseCase(
    private val sesionManager: SessionPersonRepository,
    private val eventoRepository: EventoRepository,
    private val alarmasWorker: AlarmasWorker,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun ejecutar(subscriber: CompletableObserver) {
        val completable = programarAlarmas()

        execute(completable, subscriber)
    }

    fun programarAlarmas(): Completable {
        return Completable.create {
            programarAlarmasAsync()
            it.onComplete()
        }
    }

    fun programarAlarmasAsync() {
        val sesion = requireNotNull(sesionManager.get())
        alarmasWorker.eliminarTodas()

        eventoRepository.recuperar(sesion.llaveUA)
            .mapNotNull { eventos -> crearAlarmaParaEvento(eventos) }
            .filter { alarma -> alarma.activacionPendiente }
            .forEach { alarma -> alarmasWorker.programar(alarma) }
    }

    fun eliminarAlarmas() {
        alarmasWorker.eliminarTodas()
    }

    private fun crearAlarmaParaEvento(evento: EventoRdd): Alarma.AlarmaEvento? {
        val sesion = requireNotNull(sesionManager.get())

        return Alarma.AlarmaEvento(
            fecha = evento.fechaAlarma?.time ?: return null,
            persona = sesion.persona,
            evento = evento
        )
    }
}
