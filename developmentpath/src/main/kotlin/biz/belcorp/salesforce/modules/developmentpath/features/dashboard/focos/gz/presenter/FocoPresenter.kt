package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gz.presenter

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.FocosZonaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.FocoModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.FocosGzViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gz.view.FocosView

class FocoPresenter(
    private val view: FocosView,
    private val focosZonaUseCase: FocosZonaUseCase,
    private val focoModelMapper: FocoModelMapper
) {

    lateinit var viewModel: FocosGzViewModel

    fun recargarSecciones() {
        focosZonaUseCase.obtener(FocosSubscriber())
    }

    fun cargarModelo() {
        view?.cargarSecciones(viewModel.secciones)
    }

    private inner class FocosSubscriber : BaseObserver<FocosZonaUseCase.Response>() {

        override fun onNext(t: FocosZonaUseCase.Response) {
            doAsync {
                viewModel = focoModelMapper.map(t)
                uiThread {
                    cargarModelo()
                }
            }
        }

        override fun onError(exception: Throwable) {
            Logger.loge(FocoPresenter::class.java.simpleName, exception.localizedMessage)
        }
    }
}
