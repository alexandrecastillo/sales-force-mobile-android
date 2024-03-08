package biz.belcorp.salesforce.modules.inspires.features.travel

import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.base.Presenter
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.inspires.core.domain.entities.InspiraIndicador
import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.GetIndicatorUseCase
import biz.belcorp.salesforce.modules.inspires.mapper.InspireModelDataMapper
import biz.belcorp.salesforce.modules.inspires.util.isPeru

class InspireTravelPresenter
constructor(
    private val useCaseIndicator: GetIndicatorUseCase,
    private val getSesionUseCase: ObtenerSesionUseCase,
    private val mapper: InspireModelDataMapper
) : Presenter {

    private lateinit var view: InspireTravelView
    private val session get() = requireNotNull(getSesionUseCase.obtener())

    fun create(view: InspireTravelView) {
        this.view = view
    }

    fun onPrepare() {
        useCaseIndicator.one(InspiraIndicatorSubscriber())
    }

    override fun destroy() {
        useCaseIndicator.dispose()
        //getSesionUseCase.dispose()
    }

    private fun isLimited(): Boolean = session.pais.isPeru()

    private inner class InspiraIndicatorSubscriber : BaseSingleObserver<InspiraIndicador>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: InspiraIndicador) {
            mapper.transformIndicator(t).let { model ->
                view.showHeaderCard(model.activa, isLimited())
                view.createTabs(model.activa, isLimited())
            }
        }
    }
}
