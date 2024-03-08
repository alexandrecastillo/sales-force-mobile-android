package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.consultora

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.Consultora
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.FilterSearchConsultant
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.consultoras.ConsultoraRepository

class ConsultoraUseCase(
    private val consultoraRepository: ConsultoraRepository,
    private val sesionRepository: SessionRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private val session get() = requireNotNull(sesionRepository.getSession())

    fun consultoras(
        sectionCode: String?,
        idIndicator: Int,
        idList: Int,
        observer: BaseObserver<List<Consultora>>
    ) {
        val observable = consultoraRepository.consultoras(sectionCode, idIndicator, idList)
        execute(observable, observer)
    }

    fun find(params: FilterSearchConsultant, observer: BaseObserver<List<Consultora>>) {
        val observable = consultoraRepository.find(params)

        execute(observable, observer)
    }

    fun findSize(params: FilterSearchConsultant, observer: BaseObserver<Int>) {
        val observable = consultoraRepository.findSize(params)

        execute(observable, observer)
    }

    fun getBeautyConsultantsByLevel(
        level: String = EMPTY_STRING,
        section: String = EMPTY_STRING,
        observer: BaseObserver<List<Consultora>>
    ) {
        val mySection = if (section.isBlank()) session.seccion.orEmpty() else section
        val observable = consultoraRepository
            .findPDV(level, mySection).toObservable()
        execute(observable, observer)
    }

    fun getBeautyConsultants(
        section: String = EMPTY_STRING,
        observer: BaseObserver<List<Consultora>>
    ) {
        val observable = consultoraRepository.findPDV(EMPTY_STRING, section).toObservable()
        execute(observable, observer)
    }

    fun getPossibleLevelChanges(observer: BaseObserver<List<Consultora>>, seccion: String = "%") {
        val observable = consultoraRepository.getPossibleLevelChanges(seccion).toObservable()
        execute(observable, observer)
    }

    fun getEndperiod(
        level: String = "%",
        section: String = "%",
        observer: BaseObserver<List<Consultora>>
    ) {
        val observable = consultoraRepository.getEndPeriod(level, section).toObservable()
        execute(observable, observer)
    }

    suspend fun getConsultant(id: Int): Consultora {
        return consultoraRepository.getConsultant(id)
    }

}
