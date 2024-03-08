package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.geolocation

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.geolocation.GeolocationRepository

class GeolocationUseCase(
    private val mGeolocationRepository: GeolocationRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun saveGeolocation(
        codigo: String?,
        latitud: String,
        longitud: String,
        observer: BaseCompletableObserver
    ) {
        val observable = mGeolocationRepository.saveGeolocation(codigo, latitud, longitud)
        execute(observable, observer)
    }

}
