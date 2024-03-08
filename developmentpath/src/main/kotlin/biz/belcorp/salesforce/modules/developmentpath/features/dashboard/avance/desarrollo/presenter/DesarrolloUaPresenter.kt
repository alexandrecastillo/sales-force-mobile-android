package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.presenter

import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desarrollo.RecuperarTituloDesarrolloUaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.DesarrolloUaView

class DesarrolloUaPresenter(
    private val view: DesarrolloUaView,
    private val useCase: RecuperarTituloDesarrolloUaUseCase
) {

    fun recuperar(planId: Long) {
        useCase.ejecutar(planId, RecuperarUaYCampaniaObserver())
    }

    private inner class RecuperarUaYCampaniaObserver :
        BaseSingleObserver<RecuperarTituloDesarrolloUaUseCase.Response>() {

        override fun onSuccess(t: RecuperarTituloDesarrolloUaUseCase.Response) {
            view.pintarTexto(
                t.unidadAdministrativa,
                t.campaniaAnterior.nombreCorto
            )

            mostraPantallaDesarrollo(t.rolPlan, t.infoPlan)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun mostraPantallaDesarrollo(rol: Rol, infoPlan: InfoPlanRdd) {
        when (rol) {
            Rol.DIRECTOR_VENTAS -> view.irADesarrolloPaisAlHacerClickEnCard()
            Rol.GERENTE_REGION -> view.irADesarrolloRegionAlHacerClickEnCard(infoPlan)
            Rol.GERENTE_ZONA, Rol.SOCIA_EMPRESARIA ->
                view.irADesarrolloComportamientosAlHacerClickEnCard()
            else -> throw UnsupportedRoleException(rol)
        }
    }
}
