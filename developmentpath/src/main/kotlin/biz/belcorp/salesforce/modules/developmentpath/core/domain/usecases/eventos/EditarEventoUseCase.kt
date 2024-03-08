package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRddModeloEdicion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos.EventoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository

class EditarEventoUseCase(eventoRepository: EventoRepository,
                          campaniaRepository: CampaniasRepository,
                          sessionManager: SessionPersonRepository,
                          programadorAlarmas: ProgramarAlarmasEventosUseCase,
                          threadExecutor: ThreadExecutor,
                          postExecutionThread: PostExecutionThread
) :
    CambioEventoUseCase(
        eventoRepository = eventoRepository,
        campaniaRepository = campaniaRepository,
        programadorAlarmas = programadorAlarmas,
        sesionManager = sessionManager,
        threadExecutor = threadExecutor,
        postExecutionThread = postExecutionThread) {

    fun editar(evento: EventoRddModeloEdicion, observer: BaseCompletableObserver) {
        val completable = validarFechas(evento)
            .andThen(eventoRepository.editar(evento))
            .andThen(programarAlarmas())
            .doAfterTerminate { enviarAServidor() }

        execute(completable, observer)
    }
}
