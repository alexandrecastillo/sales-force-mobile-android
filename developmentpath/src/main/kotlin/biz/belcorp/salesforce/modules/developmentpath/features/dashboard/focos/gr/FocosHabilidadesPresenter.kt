package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.FocosHabilidadesEquipoUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.FocoRegionModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.FocosHabilidadesPorUaViewModel

class FocosHabilidadesPresenter(
    private val view: FocosHabilidadesView,
    private val focosHabilidadesEquipoUseCase: FocosHabilidadesEquipoUseCase,
    private val focoModelMapper: FocoRegionModelMapper
) {

    lateinit var viewModel: FocosHabilidadesPorUaViewModel

    fun recargar(itemPosition: Int = -1) {
        focosHabilidadesEquipoUseCase.obtener(FocosHabilidadesSubscriber(itemPosition))
    }

    private fun cargarModelo(itemPosition: Int) {
        if (itemPosition >= 0) {
            view.updateItem(viewModel.uas, itemPosition)
        } else {
            view.cargar(viewModel.uas)
        }
    }

    fun obtenerRol() : String {
        return focosHabilidadesEquipoUseCase.obtenerRol()
    }

    private inner class FocosHabilidadesSubscriber(private val itemPosition: Int) :
            BaseSingleObserver<FocosHabilidadesEquipoUseCase.Response>() {

        override fun onSuccess(t: FocosHabilidadesEquipoUseCase.Response) {
            doAsync {
                viewModel = focoModelMapper.map(t)
                uiThread { cargarModelo(itemPosition) }
            }
        }

        override fun onError(e: Throwable) {
            Logger.loge("Tab focos", e.localizedMessage, e)
        }
    }
}
