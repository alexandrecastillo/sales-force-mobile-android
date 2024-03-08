package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.AvanceRegionesUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.mapper.AvanceRegionesMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.view.AvanceRegionesView

class AvanceRegionesPresenter(private val view: AvanceRegionesView,
                              private val useCase: AvanceRegionesUseCase,
                              private val mapper: AvanceRegionesMapper
) {

    fun recuperarAvance() {
        useCase.recuperar(AvanceRegionesObsever())
    }

    private inner class AvanceRegionesObsever: BaseSingleObserver<AvanceRegionesUseCase.Response>() {

        override fun onError(e: Throwable) = e.printStackTrace()

        override fun onSuccess(t: AvanceRegionesUseCase.Response) {
            pintarRegiones(t.regiones)
        }
    }

    private fun pintarRegiones(regiones: List<RegionRdd>) {
        doAsync {
            val regionesModel = mapper.parse(regiones)
            uiThread {
                view.pintarRegiones(regionesModel)
            }
        }
    }
}
