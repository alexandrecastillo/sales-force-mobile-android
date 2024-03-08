package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.misvisitas

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPropias
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.ObtenerMisVisitasPropiasUseCase

class MisVisitasPresenter(
    private val view: MisVisitasView,
    private val useCase: ObtenerMisVisitasPropiasUseCase
) {

    fun obtener() {
        useCase.obtener(MisVisitasSubscriber())
    }

    inner class MisVisitasSubscriber : BaseSingleObserver<VisitasPropias>() {
        override fun onSuccess(t: VisitasPropias) {
            view.pintar(t.visitadas, t.planificadas)
        }
    }
}
