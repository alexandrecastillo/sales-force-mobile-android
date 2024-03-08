package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.last6campaigns

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desempenio.ObtenerDesempenioGrGzUseCase

class DesempenioGzSubscriber(private val viewGr: DesempenioGrGzView,
                             private val mapperGr: DesempenioGrGzModelMapper) : BaseSingleObserver<ObtenerDesempenioGrGzUseCase.Response>() {

    override fun onSuccess(t: ObtenerDesempenioGrGzUseCase.Response) {
        val desempenios = t.desempenioU6C.map {
            mapperGr.parse(it)
        }
        viewGr.pintar(desempenios)
    }
}
