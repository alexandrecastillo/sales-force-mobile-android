package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.ZonasProductividad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.productivas.ProductivasU3CUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.model.ZonasProductivasMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.view.ProductivasU3CView
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class ProductivasU3CPresenter(
    private val view: ProductivasU3CView,
    private val useCase: ProductivasU3CUseCase,
    private val mapper: ZonasProductivasMapper
) {

    fun recuperarInformacion(personaId: Long) {
        useCase.ejecutar(personaId, Productivas3UCObserver())
    }

    fun tratarRespuesta(zonasProductivas: ZonasProductividad) {
        doAsync {
            val modelos = mapper.map(zonasProductivas)
            uiThread {
                view.pintarZonas(modelos)
            }
        }
    }

    inner class Productivas3UCObserver : BaseSingleObserver<ZonasProductividad>() {
        override fun onSuccess(t: ZonasProductividad) = tratarRespuesta(t)
        override fun onError(e: Throwable) = e.printStackTrace()
    }
}
