package biz.belcorp.salesforce.modules.postulants.core.domain.usecases

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.DetailPlace
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.maps.Place
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.MapsRepository

class MapsUseCase(
    private val mapsRepository: MapsRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun buscarLugares(
        isoPais: String, cadenaBusqueda: String, observer: BaseSingleObserver<List<Place>>
    ) {
        val observable = mapsRepository.buscarLugares(isoPais, cadenaBusqueda)
        execute(observable, observer)
    }

    fun buscarDetalleLugarGoogle(
        detalleLugarId: String, observer: BaseSingleObserver<DetailPlace?>
    ) {
        val observable = mapsRepository.buscarDetalleLugar(detalleLugarId)
        execute(observable, observer)
    }

}
