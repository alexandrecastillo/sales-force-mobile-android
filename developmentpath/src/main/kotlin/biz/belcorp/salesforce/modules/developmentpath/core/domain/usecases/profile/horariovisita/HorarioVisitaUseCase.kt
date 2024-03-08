package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.horariovisita

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.horariovisita.HorarioVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.horariovisita.HorarioVisitaConsultora
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.HorarioVisitaRepository
import io.reactivex.CompletableObserver

class HorarioVisitaUseCase(
    private val horarioVisitaRepository: HorarioVisitaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun obtenerHorarios(consultoraId: Long): List<HorarioVisita> {
        return horarioVisitaRepository.obtener(consultoraId)
    }

    fun saveHorarioOffLline(value: HorarioVisitaConsultora, subscriber: CompletableObserver) {
        val observable = horarioVisitaRepository.saveHorarioOffLline(value)
        execute(observable, subscriber)
    }

    fun getConsultant(consultoraId: Int): ConsultoraEntity? {
        return horarioVisitaRepository.getConsultant(consultoraId)
    }
}
