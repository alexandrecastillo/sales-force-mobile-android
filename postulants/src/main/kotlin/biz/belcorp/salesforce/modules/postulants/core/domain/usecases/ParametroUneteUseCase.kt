package biz.belcorp.salesforce.modules.postulants.core.domain.usecases

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ParametroUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.ParametroUneteRepository

class ParametroUneteUseCase(
    private val repository: ParametroUneteRepository,
    threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun list(useCaseSubscriber: BaseSingleObserver<List<ParametroUnete>>, tipoParametro: Int) {
        val observable = repository.list(tipoParametro)
        execute(observable, useCaseSubscriber)
    }

    fun list2(
        tipoParametro: Int,
        nombre: String,
        useCaseSubscriber: BaseSingleObserver<List<ParametroUnete>>
    ) {
        val observable = repository.list2(tipoParametro, nombre)
        execute(observable, useCaseSubscriber)
    }

    fun list(
        useCaseSubscriber: BaseSingleObserver<ParametroUnete>,
        tipoParametro: Int, nombre: String
    ) {
        val observable = repository.list(tipoParametro, nombre)
        execute(observable, useCaseSubscriber)
    }

    fun listByPadre(
        useCaseSubscriber: BaseSingleObserver<List<ParametroUnete>>,
        parametrounete: Int
    ) {
        val observable = repository.listByPadre(parametrounete)
        execute(observable, useCaseSubscriber)
    }

    fun listZonasSMS(
        useCaseSubscriber: BaseSingleObserver<List<ParametroUnete>>,
        parametrounete: Int, zona: String
    ) {
        val observable = repository.listZonasSMS(parametrounete, zona)
        execute(observable, useCaseSubscriber)
    }

    fun getParametroUnete(
        useCaseSubscriber: BaseSingleObserver<List<ParametroUnete>>,
        parametrounete: Int, zona: String
    ) {
        val observable = repository.getParametroUnete(parametrounete, zona)
        execute(observable, useCaseSubscriber)
    }

    fun getParametroUnete(
        useCaseSubscriber: BaseSingleObserver<List<ParametroUnete>>,
        parametrounete: Int
    ) {
        val observable = repository.getParametroUnete(parametrounete)
        execute(observable, useCaseSubscriber)
    }

}
