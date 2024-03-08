package biz.belcorp.salesforce.modules.inspires.features.dashboard

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.base.Presenter
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetIndicatorUseCase
import biz.belcorp.salesforce.modules.inspires.mapper.InspireModelDataMapper

class InspireDashboardPresenter
constructor(
    private val getIndicator: GetIndicatorUseCase,
    private val getCampaign: ObtenerCampaniasUseCase,
    private val getSesionUseCase: ObtenerSesionUseCase,
    private val mapper: InspireModelDataMapper
) : Presenter {

    private lateinit var view: InspireDashboardView
    private val session get() = requireNotNull(getSesionUseCase.obtener())

    fun create(view: InspireDashboardView) {
        this.view = view
    }

    fun onPrepare() {
        getIndicator.one(InspiraIndicatorSubscriber())
        getCampaign.obtenerCampaniaActual(CurrentCampaignSubscriber())
    }

    fun loadDestinationImage() {
        session.pais?.codigoIso?.let { view.showDestinationImage(it) }
    }

    override fun destroy() {
        getIndicator.dispose()
        //getSesionUseCase.dispose()
        getCampaign.dispose()
    }

    private inner class InspiraIndicatorSubscriber : BaseSingleObserver<InspiraIndicador>() {
        override fun onError(e: Throwable) {
            view.showError()
        }

        override fun onSuccess(t: InspiraIndicador) {
            val model = mapper.transformIndicator(t)
            view.setListeners()
            view.showValues(model)
        }
    }

    private inner class CurrentCampaignSubscriber : BaseSingleObserver<Campania>() {
        override fun onSuccess(t: Campania) {
            val campania = "${t.periodo} ${t.nombreCorto}"
            view.showCampaign(campania)
        }

        override fun onError(e: Throwable) {
            view.showCampaign(Constant.HYPHEN)
        }
    }
}
