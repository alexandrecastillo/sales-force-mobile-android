package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancevisitasequipos

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.ObtenerAvanceVisitasMiEquipoUseCase

class AvanceVisitasEquiposPresenter(
    private val view: AvanceVisitasMiEquipoView,
    private val useCase: ObtenerAvanceVisitasMiEquipoUseCase
) {

    fun obtener() {
        useCase.obtenerAvanceVisitas(AvanceVisitasEquiposSubscriber())
    }

    inner class AvanceVisitasEquiposSubscriber : BaseSingleObserver<ObtenerAvanceVisitasMiEquipoUseCase.AvanceVisitasMiEquipo>() {
        override fun onSuccess(t: ObtenerAvanceVisitasMiEquipoUseCase.AvanceVisitasMiEquipo) {
            view.pintarAvanceGr(t.avanceGr)
            view.pintarAvanceGz(t.avanceGz)
            view.pintarAvanceSe(t.avanceSe)
        }
    }
}
