package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.maps

import biz.belcorp.salesforce.core.utils.Logger.loge
import biz.belcorp.salesforce.core.features.base.Presenter
import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.modules.developmentpath.common.exceptions.ErrorMessageFactory2
import biz.belcorp.salesforce.modules.developmentpath.core.domain.exceptions.DefaultErrorBundle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.exceptions.ErrorBundle
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.geolocation.GeolocationUseCase

class GeolocationPresenter(
    private val geolocationView: GeolocationView,
    private val useCase: GeolocationUseCase,
    private val errorMessageFactory: ErrorMessageFactory2
) : Presenter {


    override fun destroy() = Unit

    fun saveGeolocation(codigo: String?, latitud: Double, longitud: Double) {
        useCase.saveGeolocation(
            codigo,
            latitud.toString(),
            longitud.toString(),
            GeolocationSubscriber()
        )
        geolocationView.showLoading()
    }

    private fun hideViewLoading() {
        geolocationView.hideLoading()
    }

    private fun showErrorMessage(errorBundle: ErrorBundle) {
        errorMessageFactory.create(errorBundle.exception) {
            onDefaultError { geolocationView.showError(it) }
        }
    }

    private inner class GeolocationSubscriber : BaseCompletableObserver() {

        override fun onComplete() {
            hideViewLoading()
            geolocationView.showPointSuccessLocation()
        }

        override fun onError(e: Throwable) {
            loge(javaClass.simpleName, e.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(e as Exception))
        }

    }

}
