package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.PeticionRutaOptima
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.RespuestaRuta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.RutaOptimaRepository

class RecuperarRutaUseCase(
    private val rutaOptimaRepository: RutaOptimaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun recuperarRuta(
        peticionRuta: PeticionRutaOptima,
        observer: BaseSingleObserver<List<RespuestaRuta.LatLon>>
    ) {
        val observable = rutaOptimaRepository.solicitarRuta(peticionRuta)
        execute(observable, observer)
    }
}
